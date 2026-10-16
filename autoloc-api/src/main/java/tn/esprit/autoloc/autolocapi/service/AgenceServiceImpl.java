package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

  private final IAgenceRepository agenceRepository;

  @Override
  public Agence create(Agence agence) {
    validateAgence(agence);
    if (agence.getIdAgence() != null) {
      throw new IllegalArgumentException("L'identifiant de l'agence doit être null pour une création");
    }
    return agenceRepository.save(agence);
  }

  @Override
  public Agence findById(Long id) {
    return agenceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
  }

  @Override
  public List<Agence> findAll() {
    return agenceRepository.findAll();
  }

  @Override
  public Agence update(Long id, Agence agence) {
    Agence existing = findById(id);
    validateAgence(agence);
    existing.setNom(agence.getNom());
    existing.setVille(agence.getVille());
    existing.setAdresse(agence.getAdresse());
    existing.setTelephone(agence.getTelephone());
    return agenceRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!agenceRepository.existsById(id)) {
      throw new ResourceNotFoundException("Agence", id);
    }
    agenceRepository.deleteById(id);
  }

  @Override
  public Agence saveAgence(Agence agence) {
    return create(agence);
  }

  @Override
  public Optional<Agence> getAgenceById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Agence> getAllAgences() {
    return findAll();
  }

  @Override
  public void deleteAgence(Long id) {
    deleteById(id);
  }

  private void validateAgence(Agence agence) {
    if (agence == null) {
      throw new IllegalArgumentException("L'agence ne peut pas être nulle");
    }
    if (agence.getNom() == null || agence.getNom().isBlank()) {
      throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
    }
    if (agence.getVille() == null || agence.getVille().isBlank()) {
      throw new IllegalArgumentException("La ville de l'agence est obligatoire");
    }
    if (agence.getAdresse() == null || agence.getAdresse().isBlank()) {
      throw new IllegalArgumentException("L'adresse de l'agence est obligatoire");
    }
    if (agence.getTelephone() == null || agence.getTelephone().isBlank()) {
      throw new IllegalArgumentException("Le téléphone de l'agence est obligatoire");
    }
  }
}
