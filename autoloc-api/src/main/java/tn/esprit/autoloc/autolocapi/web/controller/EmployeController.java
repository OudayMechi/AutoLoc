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
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

  private final IEmployeService employeService;

  @GetMapping
  public List<Employe> getAllEmployes() {
    return employeService.findAll();
  }

  @GetMapping("/{id}")
  public Employe getEmployeById(@PathVariable Long id) {
    return employeService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
    return ResponseEntity.status(HttpStatus.CREATED).body(employeService.create(employe));
  }

  @PutMapping("/{id}")
  public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
    return employeService.update(id, employe);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
    employeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
