package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import jakarta.validation.Valid;
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
import tn.esprit.autoloc.autolocapi.service.IClientService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ClientDto;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.ClientRequestDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {

  private final IClientService clientService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les clients")
  @ApiResponse(responseCode = "200", description = "Liste des clients récupérée")
  public List<ClientDto> getAllClients() {
    return clientService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter un client")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Client récupéré"),
      @ApiResponse(responseCode = "404", description = "Client introuvable")
  })
  public ClientDto getClientById(@Parameter(description = "Identifiant du client") @PathVariable("id") Long id) {
    return mapper.toDto(clientService.findById(id));
  }

  @PostMapping
  @Operation(summary = "Créer un client")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Client créé"),
      @ApiResponse(responseCode = "400", description = "Données invalides")
  })
  public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientRequestDto client) {
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(clientService.create(mapper.toEntity(client))));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Modifier un client")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Client modifié"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "404", description = "Client introuvable")
  })
  public ClientDto updateClient(@Parameter(description = "Identifiant du client") @PathVariable("id") Long id,
                             @Valid @RequestBody ClientRequestDto client) {
    return mapper.toDto(clientService.update(id, mapper.toEntity(client)));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Supprimer un client")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Client supprimé"),
      @ApiResponse(responseCode = "404", description = "Client introuvable")
  })
  public ResponseEntity<Void> deleteClient(
      @Parameter(description = "Identifiant du client") @PathVariable("id") Long id) {
    clientService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
