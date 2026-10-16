package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;
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
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

  private final IAgenceService agenceService;

  @GetMapping
  public List<Agence> getAllAgences() {
    return agenceService.findAll();
  }

  @GetMapping("/{id}")
  public Agence getAgenceById(@PathVariable Long id) {
    return agenceService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) {
    return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(agence));
  }

  @PutMapping("/{id}")
  public Agence updateAgence(@PathVariable Long id, @RequestBody Agence agence) {
    return agenceService.update(id, agence);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
    agenceService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
