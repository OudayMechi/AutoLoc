package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;

public interface IMaintenanceService {
  Maintenance create(Maintenance maintenance);

  Maintenance findById(Long id);

  List<Maintenance> findAll();

  Maintenance update(Long id, Maintenance maintenance);

  void deleteById(Long id);

  default Maintenance saveMaintenance(Maintenance maintenance) {
    return create(maintenance);
  }

  default Optional<Maintenance> getMaintenanceById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Maintenance> getAllMaintenances() {
    return findAll();
  }

  default void deleteMaintenance(Long id) {
    deleteById(id);
  }
}
