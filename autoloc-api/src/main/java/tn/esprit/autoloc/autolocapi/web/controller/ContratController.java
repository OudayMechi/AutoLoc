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
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.service.IContratService;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

  private final IContratService contratService;

  @GetMapping
  public List<Contrat> getAllContrats() {
    return contratService.findAll();
  }

  @GetMapping("/{id}")
  public Contrat getContratById(@PathVariable Long id) {
    return contratService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
    return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(contrat));
  }

  @PutMapping("/{id}")
  public Contrat updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
    return contratService.update(id, contrat);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
    contratService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
