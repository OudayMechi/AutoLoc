package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Reservation;

public interface IReservationService {
  Reservation create(Reservation reservation);

  Reservation findById(Long id);

  List<Reservation> findAll();

  Reservation update(Long id, Reservation reservation);

  void deleteById(Long id);

  default Reservation saveReservation(Reservation reservation) {
    return create(reservation);
  }

  default Optional<Reservation> getReservationById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Reservation> getAllReservations() {
    return findAll();
  }

  default void deleteReservation(Long id) {
    deleteById(id);
  }
}
