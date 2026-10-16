package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Agence;

public interface IAgenceService {
  Agence create(Agence agence);

  Agence findById(Long id);

  List<Agence> findAll();

  Agence update(Long id, Agence agence);

  void deleteById(Long id);

  default Agence saveAgence(Agence agence) {
    return create(agence);
  }

  default Optional<Agence> getAgenceById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Agence> getAllAgences() {
    return findAll();
  }

  default void deleteAgence(Long id) {
    deleteById(id);
  }
}
