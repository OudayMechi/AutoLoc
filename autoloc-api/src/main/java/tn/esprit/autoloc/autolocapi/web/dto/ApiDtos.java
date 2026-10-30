package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.ModePaiement;
import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;

public final class ApiDtos {

  private ApiDtos() {
  }

  public record AgenceDto(Long idAgence, String nom, String ville, String adresse, String telephone) {
  }

  public record ClientDto(Long idClient, String nom, String prenom, String email, String telephone,
                          String numPermis, LocalDate dateInscription) {
  }

  public record EmployeDto(Long idEmploye, String nom, String prenom, RoleEmploye role, AgenceDto agence) {
  }

  public record EquipementDto(Long idEquipement, String libelle, Integer quantite) {
  }

  public record VehiculeDto(Long idVehicule, String immatriculation, String marque, String modele,
                            CategorieVehicule categorie, BigDecimal tarifJournalier, StatutVehicule statut,
                            AgenceDto agence, List<EquipementDto> equipements) {
  }

  public record MaintenanceDto(Long idMaintenance, LocalDate dateDebut, LocalDate dateFin, String description,
                               BigDecimal cout, VehiculeDto vehicule) {
  }

  public record ReservationDto(Long idReservation, LocalDate dateDebut, LocalDate dateFin, StatutReservation statut,
                               VehiculeDto vehicule, ClientDto client) {
  }

  public record PaiementDto(Long idPaiement, BigDecimal montant, LocalDate datePaiement, ModePaiement modePaiement,
                            Long contratId) {
  }

  public record ContratDto(Long idContrat, LocalDate dateSignature, BigDecimal montantTotal, boolean valide,
                           ReservationDto reservation, List<PaiementDto> paiements) {
  }

  public record AgenceRequestDto(
      @NotBlank @Size(max = 100) String nom,
      @NotBlank @Size(max = 50) String ville,
      @NotBlank @Size(max = 150) String adresse,
      @NotBlank @Size(max = 20) @Pattern(regexp = "^(?=.*\\d)\\+?[0-9 ()-]{6,20}$") String telephone) {
  }

  public record ClientRequestDto(
      @NotBlank @Size(max = 50) String nom,
      @NotBlank @Size(max = 50) String prenom,
      @NotBlank @Email @Size(max = 100) String email,
      @NotBlank @Size(max = 20) @Pattern(regexp = "^(?=.*\\d)\\+?[0-9 ()-]{6,20}$") String telephone,
      @NotBlank @Size(max = 50) String numPermis,
      LocalDate dateInscription) {
  }

  public record EmployeRequestDto(
      @NotBlank @Size(max = 50) String nom,
      @NotBlank @Size(max = 50) String prenom,
      @NotNull RoleEmploye role,
      @Valid AgenceReferenceDto agence) {
  }

  public record EquipementRequestDto(
      @NotBlank @Size(max = 100) String libelle,
      @PositiveOrZero Integer quantite) {
  }

  public record VehiculeRequestDto(
      @NotBlank @Size(max = 20) String immatriculation,
      @NotBlank @Size(max = 50) String marque,
      @NotBlank @Size(max = 50) String modele,
      @NotNull CategorieVehicule categorie,
      @NotNull @DecimalMin("0.0") BigDecimal tarifJournalier,
      @NotNull StatutVehicule statut,
      @Valid AgenceReferenceDto agence,
      List<@NotNull @Valid EquipementReferenceDto> equipements) {
  }

  public record MaintenanceRequestDto(
      @NotNull LocalDate dateDebut,
      LocalDate dateFin,
      @Size(max = 255) String description,
      @DecimalMin("0.0") BigDecimal cout,
      @Valid VehiculeReferenceDto vehicule) {
  }

  public record ReservationRequestDto(
      @NotNull LocalDate dateDebut,
      @NotNull LocalDate dateFin,
      @NotNull StatutReservation statut,
      @Valid VehiculeReferenceDto vehicule,
      @Valid ClientReferenceDto client) {
  }

  public record PaiementRequestDto(
      @NotNull @DecimalMin(value = "0.01") BigDecimal montant,
      @NotNull LocalDate datePaiement,
      @NotNull ModePaiement modePaiement) {
  }

  public record ContratRequestDto(
      @NotNull LocalDate dateSignature,
      @NotNull @DecimalMin("0.0") BigDecimal montantTotal,
      @NotNull Boolean valide,
      @Valid ReservationReferenceDto reservation,
      List<@NotNull @Valid PaiementRequestDto> paiements) {
  }

  public record AgenceReferenceDto(@NotNull @Positive Long idAgence) {
  }

  public record ClientReferenceDto(@NotNull @Positive Long idClient) {
  }

  public record EquipementReferenceDto(@NotNull @Positive Long idEquipement) {
  }

  public record VehiculeReferenceDto(@NotNull @Positive Long idVehicule) {
  }

  public record ReservationReferenceDto(@NotNull @Positive Long idReservation) {
  }
}
