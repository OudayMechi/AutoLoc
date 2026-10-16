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
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

  private final IMaintenanceService maintenanceService;

  @GetMapping
  public List<Maintenance> getAllMaintenances() {
    return maintenanceService.findAll();
  }

  @GetMapping("/{id}")
  public Maintenance getMaintenanceById(@PathVariable Long id) {
    return maintenanceService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Maintenance> createMaintenance(@RequestBody Maintenance maintenance) {
    return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.create(maintenance));
  }

  @PutMapping("/{id}")
  public Maintenance updateMaintenance(@PathVariable Long id, @RequestBody Maintenance maintenance) {
    return maintenanceService.update(id, maintenance);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
    maintenanceService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
