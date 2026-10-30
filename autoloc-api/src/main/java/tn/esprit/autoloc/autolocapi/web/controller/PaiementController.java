package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.service.IPaiementService;
import tn.esprit.autoloc.autolocapi.web.dto.ApiDtos.PaiementDto;
import tn.esprit.autoloc.autolocapi.web.mapper.ApiMapper;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements")
public class PaiementController {

  private final IPaiementService paiementService;
  private final ApiMapper mapper;

  @GetMapping
  @Operation(summary = "Lister les paiements")
  @ApiResponse(responseCode = "200", description = "Liste des paiements récupérée")
  public List<PaiementDto> getAllPaiements() {
    return paiementService.findAll().stream().map(mapper::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Consulter un paiement")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Paiement récupéré"),
      @ApiResponse(responseCode = "404", description = "Paiement introuvable")
  })
  public PaiementDto getPaiementById(@Parameter(description = "Identifiant du paiement") @PathVariable("id") Long id) {
    return mapper.toDto(paiementService.findById(id));
  }
}
