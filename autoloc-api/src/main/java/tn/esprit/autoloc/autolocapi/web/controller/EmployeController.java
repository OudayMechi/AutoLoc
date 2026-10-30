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
import tn.esprit.autoloc.autolocapi.service.IEmployeService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EmployeDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.EmployeRequestDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {

  private final IEmployeService employeService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les employés")
  @ApiResponse(responseCode = "200", description = "Liste des employés récupérée")
  public List<EmployeDto> getAllEmployes() {
    return employeService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter un employé")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Employé récupéré"),
      @ApiResponse(responseCode = "404", description = "Employé introuvable")
  })
  public EmployeDto getEmployeById(@Parameter(description = "Identifiant de l'employé") @PathVariable("id") Long id) {
    return mapper.toDto(employeService.findById(id));
  }

  @PostMapping
  @Operation(summary = "Créer un employé")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Employé créé"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<EmployeDto> createEmploye(@Valid @RequestBody EmployeRequestDto employe) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toDto(employeService.create(mapper.toEntity(employe))));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier un employé")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Employé modifié"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Employé introuvable")
  })
  public EmployeDto updateEmploye(@Parameter(description = "Identifiant de l'employé") @PathVariable("id") Long id,
                               @Valid @RequestBody EmployeRequestDto employe) {
    return mapper.toDto(employeService.update(id, mapper.toEntity(employe)));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer un employé")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Employé supprimé"),
      @ApiResponse(responseCode = "404", description = "Employé introuvable")
  })
  public ResponseEntity<Void> deleteEmploye(
      @Parameter(description = "Identifiant de l'employé") @PathVariable("id") Long id) {
    employeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
