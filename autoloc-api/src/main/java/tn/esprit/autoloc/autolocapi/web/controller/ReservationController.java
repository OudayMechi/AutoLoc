package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.service.IReservationService;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final IReservationService reservationService;

  @GetMapping
  public List<Reservation> getAllReservations() {
    return reservationService.findAll();
  }

  @GetMapping("/{id}")
  public Reservation getReservationById(@PathVariable Long id) {
    return reservationService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
    return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservation));
  }

  @PutMapping("/{id}")
  public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
    return reservationService.update(id, reservation);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
    reservationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
