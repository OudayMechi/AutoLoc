package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

  private final IReservationRepository reservationRepository;

  @Override
  public Reservation create(Reservation reservation) {
    validateReservation(reservation);
    if (reservation.getIdReservation() != null) {
      throw new IllegalArgumentException("L'identifiant de la réservation doit être null pour une création");
    }
    return reservationRepository.save(reservation);
  }

  @Override
  public Reservation findById(Long id) {
    return reservationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
  }

  @Override
  public List<Reservation> findAll() {
    return reservationRepository.findAll();
  }

  @Override
  public Reservation update(Long id, Reservation reservation) {
    Reservation existing = findById(id);
    validateReservation(reservation);
    existing.setDateDebut(reservation.getDateDebut());
    existing.setDateFin(reservation.getDateFin());
    existing.setStatut(reservation.getStatut());
    existing.setVehicule(reservation.getVehicule());
    existing.setClient(reservation.getClient());
    return reservationRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!reservationRepository.existsById(id)) {
      throw new ResourceNotFoundException("Reservation", id);
    }
    reservationRepository.deleteById(id);
  }

  @Override
  public Reservation saveReservation(Reservation reservation) {
    return create(reservation);
  }

  @Override
  public Optional<Reservation> getReservationById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Reservation> getAllReservations() {
    return findAll();
  }

  @Override
  public void deleteReservation(Long id) {
    deleteById(id);
  }

  private void validateReservation(Reservation reservation) {
    if (reservation == null) {
      throw new IllegalArgumentException("La réservation ne peut pas être nulle");
    }
    if (reservation.getDateDebut() == null) {
      throw new IllegalArgumentException("La date de début de la réservation est obligatoire");
    }
    if (reservation.getDateFin() == null) {
      throw new IllegalArgumentException("La date de fin de la réservation est obligatoire");
    }
    if (reservation.getDateFin().isBefore(reservation.getDateDebut())) {
      throw new IllegalArgumentException("La date de fin de la réservation ne peut pas être antérieure à la date de début");
    }
  }
}
