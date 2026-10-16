package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.repository.IEmployeRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

  private final IEmployeRepository employeRepository;

  @Override
  public Employe create(Employe employe) {
    validateEmploye(employe);
    if (employe.getIdEmploye() != null) {
      throw new IllegalArgumentException("L'identifiant de l'employé doit être null pour une création");
    }
    return employeRepository.save(employe);
  }

  @Override
  public Employe findById(Long id) {
    return employeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
  }

  @Override
  public List<Employe> findAll() {
    return employeRepository.findAll();
  }

  @Override
  public Employe update(Long id, Employe employe) {
    Employe existing = findById(id);
    validateEmploye(employe);
    existing.setNom(employe.getNom());
    existing.setPrenom(employe.getPrenom());
    existing.setRole(employe.getRole());
    existing.setAgence(employe.getAgence());
    return employeRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!employeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Employe", id);
    }
    employeRepository.deleteById(id);
  }

  @Override
  public Employe saveEmploye(Employe employe) {
    return create(employe);
  }

  @Override
  public Optional<Employe> getEmployeById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Employe> getAllEmployes() {
    return findAll();
  }

  @Override
  public void deleteEmploye(Long id) {
    deleteById(id);
  }

  private void validateEmploye(Employe employe) {
    if (employe == null) {
      throw new IllegalArgumentException("L'employé ne peut pas être nul");
    }
    if (employe.getNom() == null || employe.getNom().isBlank()) {
      throw new IllegalArgumentException("Le nom de l'employé est obligatoire");
    }
    if (employe.getPrenom() == null || employe.getPrenom().isBlank()) {
      throw new IllegalArgumentException("Le prénom de l'employé est obligatoire");
    }
    if (employe.getRole() == null) {
      throw new IllegalArgumentException("Le rôle de l'employé est obligatoire");
    }
  }
}
