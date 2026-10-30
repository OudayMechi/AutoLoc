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
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.MaintenanceRequestDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {

  private final IMaintenanceService maintenanceService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les maintenances")
  @ApiResponse(responseCode = "200", description = "Liste des maintenances récupérée")
  public List<MaintenanceDto> getAllMaintenances() {
    return maintenanceService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter une maintenance")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Maintenance récupérée"),
      @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
  })
  public MaintenanceDto getMaintenanceById(
      @Parameter(description = "Identifiant de la maintenance") @PathVariable("id") Long id) {
    return mapper.toDto(maintenanceService.findById(id));
  }

  @PostMapping
  @Operation(summary = "Créer une maintenance")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Maintenance créée"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<MaintenanceDto> createMaintenance(@Valid @RequestBody MaintenanceRequestDto maintenance) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toDto(maintenanceService.create(mapper.toEntity(maintenance))));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier une maintenance")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Maintenance modifiée"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
  })
  public MaintenanceDto updateMaintenance(
      @Parameter(description = "Identifiant de la maintenance") @PathVariable("id") Long id,
      @Valid @RequestBody MaintenanceRequestDto maintenance) {
    return mapper.toDto(maintenanceService.update(id, mapper.toEntity(maintenance)));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer une maintenance")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Maintenance supprimée"),
      @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
  })
  public ResponseEntity<Void> deleteMaintenance(
      @Parameter(description = "Identifiant de la maintenance") @PathVariable("id") Long id) {
    maintenanceService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
