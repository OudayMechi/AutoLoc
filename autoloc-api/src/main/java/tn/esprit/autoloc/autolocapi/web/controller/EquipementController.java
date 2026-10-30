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
import tn.esprit.autoloc.autolocapi.service.IEquipementService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EquipementDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EquipementRequestDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
public class EquipementController {

  private final IEquipementService equipementService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les équipements")
  @ApiResponse(responseCode = "200", description = "Liste des équipements récupérée")
  public List<EquipementDto> getAllEquipements() {
    return equipementService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter un équipement")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Équipement récupéré"),
      @ApiResponse(responseCode = "404", description = "Équipement introuvable")
  })
  public EquipementDto getEquipementById(
      @Parameter(description = "Identifiant de l'équipement") @PathVariable("id") Long id) {
    return mapper.toDto(equipementService.findById(id));
  }

  @PostMapping
  @Operation(summary = "Créer un équipement")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Équipement créé"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<EquipementDto> createEquipement(@Valid @RequestBody EquipementRequestDto equipement) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toDto(equipementService.create(mapper.toEntity(equipement))));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier un équipement")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Équipement modifié"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Équipement introuvable")
  })
  public EquipementDto updateEquipement(
      @Parameter(description = "Identifiant de l'équipement") @PathVariable("id") Long id,
      @Valid @RequestBody EquipementRequestDto equipement) {
    return mapper.toDto(equipementService.update(id, mapper.toEntity(equipement)));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer un équipement")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Équipement supprimé"),
      @ApiResponse(responseCode = "404", description = "Équipement introuvable")
  })
  public ResponseEntity<Void> deleteEquipement(
      @Parameter(description = "Identifiant de l'équipement") @PathVariable("id") Long id) {
    equipementService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
