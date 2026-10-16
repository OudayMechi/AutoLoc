package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.service.IPaiementService;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

  private final IPaiementService paiementService;

  @GetMapping
  public List<Paiement> getAllPaiements() {
    return paiementService.findAll();
  }

  @GetMapping("/{id}")
  public Paiement getPaiementById(@PathVariable Long id) {
    return paiementService.findById(id);
  }
}
