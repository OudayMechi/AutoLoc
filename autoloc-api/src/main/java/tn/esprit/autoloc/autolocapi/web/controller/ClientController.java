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
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.service.IClientService;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final IClientService clientService;

  @GetMapping
  public List<Client> getAllClients() {
    return clientService.findAll();
  }

  @GetMapping("/{id}")
  public Client getClientById(@PathVariable Long id) {
    return clientService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Client> createClient(@RequestBody Client client) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(client));
  }

  @PutMapping("/{id}")
  public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
    return clientService.update(id, client);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    clientService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
