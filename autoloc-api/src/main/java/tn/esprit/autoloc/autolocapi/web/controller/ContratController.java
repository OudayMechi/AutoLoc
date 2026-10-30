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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.service.IContratService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ContratDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ContratRequestDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats")
public class ContratController {

  private final IContratService contratService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les contrats")
  @ApiResponse(responseCode = "200", description = "Liste des contrats récupérée")
  public List<ContratDto> getAllContrats() {
    return contratService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter un contrat")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Contrat récupéré"),
      @ApiResponse(responseCode = "404", description = "Contrat introuvable")
  })
  public ContratDto getContratById(@Parameter(description = "Identifiant du contrat") @PathVariable("id") Long id) {
    return mapper.toDto(contratService.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Créer un contrat")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Contrat créé"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ContratDto createContrat(@Valid @RequestBody ContratRequestDto contrat) {
    return mapper.toDto(contratService.create(mapper.toEntity(contrat)));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier un contrat")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Contrat modifié"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Contrat introuvable")
  })
  public ContratDto updateContrat(@Parameter(description = "Identifiant du contrat") @PathVariable("id") Long id,
                               @Valid @RequestBody ContratRequestDto contrat) {
    return mapper.toDto(contratService.update(id, mapper.toEntity(contrat)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Supprimer un contrat")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Contrat supprimé"),
      @ApiResponse(responseCode = "404", description = "Contrat introuvable")
  })
  public void deleteContrat(@Parameter(description = "Identifiant du contrat") @PathVariable("id") Long id) {
    contratService.deleteById(id);
  }
}
