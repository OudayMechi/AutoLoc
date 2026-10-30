package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import tn.esprit.autoloc.autolocapi.service.IReservationService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ReservationDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ReservationRequestDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {

  private final IReservationService reservationService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les réservations")
  @ApiResponse(responseCode = "200", description = "Liste des réservations récupérée")
  public List<ReservationDto> getAllReservations() {
    return reservationService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter une réservation")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Réservation récupérée"),
      @ApiResponse(responseCode = "404", description = "Réservation introuvable")
  })
  public ReservationDto getReservationById(
      @Parameter(description = "Identifiant de la réservation") @PathVariable("id") Long id) {
    return mapper.toDto(reservationService.findById(id));
  }

  @PostMapping
  @Operation(summary = "Créer une réservation")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Réservation créée"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody ReservationRequestDto reservation) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toDto(reservationService.create(mapper.toEntity(reservation))));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier une réservation")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Réservation modifiée"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Réservation introuvable")
  })
  public ReservationDto updateReservation(
      @Parameter(description = "Identifiant de la réservation") @PathVariable("id") Long id,
      @Valid @RequestBody ReservationRequestDto reservation) {
    return mapper.toDto(reservationService.update(id, mapper.toEntity(reservation)));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer une réservation")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Réservation supprimée"),
      @ApiResponse(responseCode = "404", description = "Réservation introuvable")
  })
  public ResponseEntity<Void> deleteReservation(
      @Parameter(description = "Identifiant de la réservation") @PathVariable("id") Long id) {
    reservationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
