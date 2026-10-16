package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Employe;

public interface IEmployeService {
  Employe create(Employe employe);

  Employe findById(Long id);

  List<Employe> findAll();

  Employe update(Long id, Employe employe);

  void deleteById(Long id);

  default Employe saveEmploye(Employe employe) {
    return create(employe);
  }

  default Optional<Employe> getEmployeById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Employe> getAllEmployes() {
    return findAll();
  }

  default void deleteEmploye(Long id) {
    deleteById(id);
  }
}
