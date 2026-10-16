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
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

  private final IVehiculeService vehiculeService;

  @GetMapping
  public List<Vehicule> getAllVehicules() {
    return vehiculeService.findAll();
  }

  @GetMapping("/{id}")
  public Vehicule getVehiculeById(@PathVariable Long id) {
    return vehiculeService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
    return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(vehicule));
  }

  @PutMapping("/{id}")
  public Vehicule updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
    return vehiculeService.update(id, vehicule);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
    vehiculeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
