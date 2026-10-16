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
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.service.IEquipementService;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

  private final IEquipementService equipementService;

  @GetMapping
  public List<Equipement> getAllEquipements() {
    return equipementService.findAll();
  }

  @GetMapping("/{id}")
  public Equipement getEquipementById(@PathVariable Long id) {
    return equipementService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Equipement> createEquipement(@RequestBody Equipement equipement) {
    return ResponseEntity.status(HttpStatus.CREATED).body(equipementService.create(equipement));
  }

  @PutMapping("/{id}")
  public Equipement updateEquipement(@PathVariable Long id, @RequestBody Equipement equipement) {
    return equipementService.update(id, equipement);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEquipement(@PathVariable Long id) {
    equipementService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
