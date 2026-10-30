package tn.esprit.autoloc.autolocapi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

  private final IVehiculeRepository vehiculeRepository;
  private final IAgenceRepository agenceRepository;
  private final IEquipementRepository equipementRepository;

  @Override
  public Vehicule create(Vehicule vehicule) {
    validateVehicule(vehicule);
    if (vehicule.getIdVehicule() != null) {
      throw new IllegalArgumentException("L'identifiant du véhicule doit être null pour une création");
    }
    resolveReferences(vehicule);
    return vehiculeRepository.save(vehicule);
  }

  @Override
  public Vehicule findById(Long id) {
    return vehiculeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
  }

  @Override
  public List<Vehicule> findAll() {
    return vehiculeRepository.findAll();
  }

  @Override
  public Vehicule update(Long id, Vehicule vehicule) {
    Vehicule existing = findById(id);
    validateVehicule(vehicule);
    existing.setImmatriculation(vehicule.getImmatriculation());
    existing.setMarque(vehicule.getMarque());
    existing.setModele(vehicule.getModele());
    existing.setCategorie(vehicule.getCategorie());
    existing.setTarifJournalier(vehicule.getTarifJournalier());
    existing.setStatut(vehicule.getStatut());
    resolveReferences(vehicule);
    existing.setAgence(vehicule.getAgence());
    existing.setEquipements(vehicule.getEquipements());
    return vehiculeRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!vehiculeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Vehicule", id);
    }
    vehiculeRepository.deleteById(id);
  }

  @Override
  public Vehicule saveVehicule(Vehicule vehicule) {
    return create(vehicule);
  }

  @Override
  public Optional<Vehicule> getVehiculeById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Vehicule> getAllVehicules() {
    return findAll();
  }

  @Override
  public void deleteVehicule(Long id) {
    deleteById(id);
  }

  private void validateVehicule(Vehicule vehicule) {
    if (vehicule == null) {
      throw new IllegalArgumentException("Le véhicule ne peut pas être nul");
    }
    if (vehicule.getImmatriculation() == null || vehicule.getImmatriculation().isBlank()) {
      throw new IllegalArgumentException("L'immatriculation du véhicule est obligatoire");
    }
    if (vehicule.getMarque() == null || vehicule.getMarque().isBlank()) {
      throw new IllegalArgumentException("La marque du véhicule est obligatoire");
    }
    if (vehicule.getModele() == null || vehicule.getModele().isBlank()) {
      throw new IllegalArgumentException("Le modèle du véhicule est obligatoire");
    }
    if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Le tarif journalier du véhicule ne peut pas être négatif");
    }
  }

  private void resolveReferences(Vehicule vehicule) {
    Agence agence = vehicule.getAgence();
    if (agence != null) {
      vehicule.setAgence(agenceRepository.findById(agence.getIdAgence())
          .orElseThrow(() -> new ResourceNotFoundException("Agence", agence.getIdAgence())));
    }

    List<Equipement> equipements = vehicule.getEquipements();
    if (equipements != null) {
      vehicule.setEquipements(equipements.stream()
          .map(equipement -> equipementRepository.findById(equipement.getIdEquipement())
              .orElseThrow(() -> new ResourceNotFoundException("Equipement", equipement.getIdEquipement())))
          .toList());
    }
  }
}
