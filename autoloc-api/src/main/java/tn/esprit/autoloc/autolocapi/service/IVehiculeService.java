package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IVehiculeService {
  Vehicule create(Vehicule vehicule);

  Vehicule findById(Long id);

  List<Vehicule> findAll();

  Vehicule update(Long id, Vehicule vehicule);

  void deleteById(Long id);

  default Vehicule saveVehicule(Vehicule vehicule) {
    return create(vehicule);
  }

  default Optional<Vehicule> getVehiculeById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Vehicule> getAllVehicules() {
    return findAll();
  }

  default void deleteVehicule(Long id) {
    deleteById(id);
  }
}
