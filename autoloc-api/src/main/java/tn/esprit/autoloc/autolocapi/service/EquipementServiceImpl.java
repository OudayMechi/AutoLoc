package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

  private final IEquipementRepository equipementRepository;

  @Override
  public Equipement create(Equipement equipement) {
    validateEquipement(equipement);
    if (equipement.getIdEquipement() != null) {
      throw new IllegalArgumentException("L'identifiant de l'équipement doit être null pour une création");
    }
    return equipementRepository.save(equipement);
  }

  @Override
  public Equipement findById(Long id) {
    return equipementRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
  }

  @Override
  public List<Equipement> findAll() {
    return equipementRepository.findAll();
  }

  @Override
  public Equipement update(Long id, Equipement equipement) {
    Equipement existing = findById(id);
    validateEquipement(equipement);
    existing.setLibelle(equipement.getLibelle());
    return equipementRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!equipementRepository.existsById(id)) {
      throw new ResourceNotFoundException("Equipement", id);
    }
    equipementRepository.deleteById(id);
  }

  @Override
  public Equipement saveEquipement(Equipement equipement) {
    return create(equipement);
  }

  @Override
  public Optional<Equipement> getEquipementById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Equipement> getAllEquipements() {
    return findAll();
  }

  @Override
  public void deleteEquipement(Long id) {
    deleteById(id);
  }

  private void validateEquipement(Equipement equipement) {
    if (equipement == null) {
      throw new IllegalArgumentException("L'équipement ne peut pas être nul");
    }
    if (equipement.getLibelle() == null || equipement.getLibelle().isBlank()) {
      throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
    }
  }
}
