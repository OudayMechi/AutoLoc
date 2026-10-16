package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Contrat;

public interface IContratService {
  Contrat create(Contrat contrat);

  Contrat findById(Long id);

  List<Contrat> findAll();

  Contrat update(Long id, Contrat contrat);

  void deleteById(Long id);

  default Contrat saveContrat(Contrat contrat) {
    return create(contrat);
  }

  default Optional<Contrat> getContratById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Contrat> getAllContrats() {
    return findAll();
  }

  default void deleteContrat(Long id) {
    deleteById(id);
  }
}
