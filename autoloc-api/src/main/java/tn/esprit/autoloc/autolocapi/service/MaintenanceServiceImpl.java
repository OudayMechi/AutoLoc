package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

  private final IMaintenanceRepository maintenanceRepository;

  @Override
  public Maintenance create(Maintenance maintenance) {
    validateMaintenance(maintenance);
    if (maintenance.getIdMaintenance() != null) {
      throw new IllegalArgumentException("L'identifiant de la maintenance doit être null pour une création");
    }
    return maintenanceRepository.save(maintenance);
  }

  @Override
  public Maintenance findById(Long id) {
    return maintenanceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
  }

  @Override
  public List<Maintenance> findAll() {
    return maintenanceRepository.findAll();
  }

  @Override
  public Maintenance update(Long id, Maintenance maintenance) {
    Maintenance existing = findById(id);
    validateMaintenance(maintenance);
    existing.setDateDebut(maintenance.getDateDebut());
    existing.setDateFin(maintenance.getDateFin());
    existing.setDescription(maintenance.getDescription());
    existing.setVehicule(maintenance.getVehicule());
    return maintenanceRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!maintenanceRepository.existsById(id)) {
      throw new ResourceNotFoundException("Maintenance", id);
    }
    maintenanceRepository.deleteById(id);
  }

  @Override
  public Maintenance saveMaintenance(Maintenance maintenance) {
    return create(maintenance);
  }

  @Override
  public Optional<Maintenance> getMaintenanceById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Maintenance> getAllMaintenances() {
    return findAll();
  }

  @Override
  public void deleteMaintenance(Long id) {
    deleteById(id);
  }

  private void validateMaintenance(Maintenance maintenance) {
    if (maintenance == null) {
      throw new IllegalArgumentException("La maintenance ne peut pas être nulle");
    }
    if (maintenance.getDateDebut() == null) {
      throw new IllegalArgumentException("La date de début de la maintenance est obligatoire");
    }
    if (maintenance.getDateFin() != null && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
      throw new IllegalArgumentException("La date de fin de la maintenance ne peut pas être antérieure à la date de début");
    }
  }
}
