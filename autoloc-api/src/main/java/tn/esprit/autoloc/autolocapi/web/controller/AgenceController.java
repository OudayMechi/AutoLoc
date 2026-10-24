package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Parameter;
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
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.service.IAgenceService;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {

  private final IAgenceService agenceService;

  @GetMapping
  @Operation(summary = "Lister les agences")
  @ApiResponse(responseCode = "200", description = "Liste des agences récupérée")
  public List<Agence> getAllAgences() {
    return agenceService.findAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter une agence")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Agence récupérée"),
      @ApiResponse(responseCode = "404", description = "Agence introuvable")
  })
  public Agence getAgenceById(@Parameter(description = "Identifiant de l'agence") @PathVariable("id") Long id) {
    return agenceService.findById(id);
  }

  @PostMapping
  @Operation(summary = "Créer une agence")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Agence créée"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) {
    return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(agence));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier une agence")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Agence modifiée"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Agence introuvable")
  })
  public Agence updateAgence(@Parameter(description = "Identifiant de l'agence") @PathVariable("id") Long id,
                             @RequestBody Agence agence) {
    return agenceService.update(id, agence);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer une agence")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Agence supprimée"),
      @ApiResponse(responseCode = "404", description = "Agence introuvable")
  })
  public ResponseEntity<Void> deleteAgence(
      @Parameter(description = "Identifiant de l'agence") @PathVariable("id") Long id) {
    agenceService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
