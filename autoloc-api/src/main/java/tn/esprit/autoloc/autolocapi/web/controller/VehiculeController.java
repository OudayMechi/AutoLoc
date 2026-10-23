package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
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
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {

  private final IVehiculeService vehiculeService;

  @GetMapping
  @Operation(summary = "Lister les véhicules")
  @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée")
  public List<Vehicule> getAllVehicules() {
    return vehiculeService.findAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter un véhicule")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Véhicule récupéré"),
      @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
  })
  public Vehicule getVehiculeById(@PathVariable Long id) {
    return vehiculeService.findById(id);
  }

  @PostMapping
  @Operation(summary = "Créer un véhicule")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Véhicule créé"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
    return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(vehicule));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier un véhicule")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Véhicule modifié"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
  })
  public Vehicule updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
    return vehiculeService.update(id, vehicule);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer un véhicule")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Véhicule supprimé"),
      @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
  })
  public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
    vehiculeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
