package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Equipement;

public interface IEquipementService {
  Equipement create(Equipement equipement);

  Equipement findById(Long id);

  List<Equipement> findAll();

  Equipement update(Long id, Equipement equipement);

  void deleteById(Long id);

  default Equipement saveEquipement(Equipement equipement) {
    return create(equipement);
  }

  default Optional<Equipement> getEquipementById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Equipement> getAllEquipements() {
    return findAll();
  }

  default void deleteEquipement(Long id) {
    deleteById(id);
  }
}
