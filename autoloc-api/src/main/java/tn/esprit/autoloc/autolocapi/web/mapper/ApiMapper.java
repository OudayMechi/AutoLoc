package tn.esprit.autoloc.autolocapi.web.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.AgenceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.AgenceReferenceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.AgenceRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ClientDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ClientReferenceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ClientRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ContratDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ContratRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EmployeDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EmployeRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EquipementDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EquipementReferenceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EquipementRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.MaintenanceRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.PaiementDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.PaiementRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ReservationDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ReservationReferenceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ReservationRequestDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.VehiculeDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.VehiculeReferenceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.VehiculeRequestDto;

@Component
public class ApiMapper {

  public Agence toEntity(AgenceRequestDto dto) {
    return new Agence(null, dto.nom(), dto.ville(), dto.adresse(), dto.telephone(), null, null);
  }

  public AgenceDto toDto(Agence entity) {
    if (entity == null) {
      return null;
    }
    return new AgenceDto(entity.getIdAgence(), entity.getNom(), entity.getVille(), entity.getAdresse(),
        entity.getTelephone());
  }

  public Client toEntity(ClientRequestDto dto) {
    return new Client(null, dto.nom(), dto.prenom(), dto.email(), dto.telephone(), dto.numPermis(),
        dto.dateInscription(), null);
  }

  public ClientDto toDto(Client entity) {
    if (entity == null) {
      return null;
    }
    return new ClientDto(entity.getIdClient(), entity.getNom(), entity.getPrenom(), entity.getEmail(),
        entity.getTelephone(), entity.getNumPermis(), entity.getDateInscription());
  }

  public Employe toEntity(EmployeRequestDto dto) {
    return new Employe(null, dto.nom(), dto.prenom(), dto.role(), toEntity(dto.agence()));
  }

  public EmployeDto toDto(Employe entity) {
    if (entity == null) {
      return null;
    }
    return new EmployeDto(entity.getIdEmploye(), entity.getNom(), entity.getPrenom(), entity.getRole(),
        toDto(entity.getAgence()));
  }

  public Equipement toEntity(EquipementRequestDto dto) {
    return new Equipement(null, dto.libelle(), dto.quantite(), null);
  }

  public EquipementDto toDto(Equipement entity) {
    if (entity == null) {
      return null;
    }
    return new EquipementDto(entity.getIdEquipement(), entity.getLibelle(), entity.getQuantite());
  }

  public Vehicule toEntity(VehiculeRequestDto dto) {
    List<Equipement> equipements = dto.equipements() == null ? null
        : dto.equipements().stream().map(ApiMapper::toEntity).collect(Collectors.toList());
    return new Vehicule(null, dto.immatriculation(), dto.marque(), dto.modele(), dto.categorie(),
        dto.tarifJournalier(), dto.statut(), toEntity(dto.agence()), equipements, null, null);
  }

  public VehiculeDto toDto(Vehicule entity) {
    if (entity == null) {
      return null;
    }
    List<EquipementDto> equipements = entity.getEquipements() == null ? null
        : entity.getEquipements().stream().map(this::toDto).toList();
    return new VehiculeDto(entity.getIdVehicule(), entity.getImmatriculation(), entity.getMarque(),
        entity.getModele(), entity.getCategorie(), entity.getTarifJournalier(), entity.getStatut(),
        toDto(entity.getAgence()), equipements);
  }

  public Maintenance toEntity(MaintenanceRequestDto dto) {
    return new Maintenance(null, dto.dateDebut(), dto.dateFin(), dto.description(), dto.cout(),
        toEntity(dto.vehicule()));
  }

  public MaintenanceDto toDto(Maintenance entity) {
    if (entity == null) {
      return null;
    }
    return new MaintenanceDto(entity.getIdMaintenance(), entity.getDateDebut(), entity.getDateFin(),
        entity.getDescription(), entity.getCout(), toDto(entity.getVehicule()));
  }

  public Reservation toEntity(ReservationRequestDto dto) {
    return new Reservation(null, dto.dateDebut(), dto.dateFin(), dto.statut(), toEntity(dto.vehicule()),
        toEntity(dto.client()), null);
  }

  public ReservationDto toDto(Reservation entity) {
    if (entity == null) {
      return null;
    }
    return new ReservationDto(entity.getIdReservation(), entity.getDateDebut(), entity.getDateFin(),
        entity.getStatut(), toDto(entity.getVehicule()), toDto(entity.getClient()));
  }

  public PaiementDto toDto(Paiement entity) {
    if (entity == null) {
      return null;
    }
    return new PaiementDto(entity.getIdPaiement(), entity.getMontant(), entity.getDatePaiement(),
        entity.getModePaiement(), entity.getContrat() == null ? null : entity.getContrat().getIdContrat());
  }

  public Contrat toEntity(ContratRequestDto dto) {
    List<Paiement> paiements = dto.paiements() == null ? null
        : dto.paiements().stream().map(this::toEntity).toList();
    return new Contrat(null, dto.dateSignature(), dto.montantTotal(), Boolean.TRUE.equals(dto.valide()),
        toEntity(dto.reservation()), paiements);
  }

  public ContratDto toDto(Contrat entity) {
    if (entity == null) {
      return null;
    }
    List<PaiementDto> paiements = entity.getPaiements() == null ? null
        : entity.getPaiements().stream().map(this::toDto).toList();
    return new ContratDto(entity.getIdContrat(), entity.getDateSignature(), entity.getMontantTotal(),
        entity.isValide(), toDto(entity.getReservation()), paiements);
  }

  private Paiement toEntity(PaiementRequestDto dto) {
    return new Paiement(null, dto.montant(), dto.datePaiement(), dto.modePaiement(), null);
  }

  private static Agence toEntity(AgenceReferenceDto dto) {
    return dto == null ? null : new Agence(dto.idAgence(), null, null, null, null, null, null);
  }

  private static Client toEntity(ClientReferenceDto dto) {
    return dto == null ? null : new Client(dto.idClient(), null, null, null, null, null, null, null);
  }

  private static Equipement toEntity(EquipementReferenceDto dto) {
    return new Equipement(dto.idEquipement(), null, null, null);
  }

  private static Vehicule toEntity(VehiculeReferenceDto dto) {
    return dto == null ? null
        : new Vehicule(dto.idVehicule(), null, null, null, null, null, null, null, null, null, null);
  }

  private static Reservation toEntity(ReservationReferenceDto dto) {
    return dto == null ? null : new Reservation(dto.idReservation(), null, null, null, null, null, null);
  }

}
