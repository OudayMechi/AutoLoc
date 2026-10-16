package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Paiement;

public interface IPaiementService {
  Paiement findById(Long id);

  List<Paiement> findAll();

  default Optional<Paiement> getPaiementById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Paiement> getAllPaiements() {
    return findAll();
  }
}
