package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

  private final IClientRepository clientRepository;

  @Override
  public Client create(Client client) {
    validateClient(client);
    if (client.getIdClient() != null) {
      throw new IllegalArgumentException("L'identifiant du client doit être null pour une création");
    }
    return clientRepository.save(client);
  }

  @Override
  public Client findById(Long id) {
    return clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Client", id));
  }

  @Override
  public List<Client> findAll() {
    return clientRepository.findAll();
  }

  @Override
  public Client update(Long id, Client client) {
    Client existing = findById(id);
    validateClient(client);
    existing.setNom(client.getNom());
    existing.setPrenom(client.getPrenom());
    existing.setEmail(client.getEmail());
    existing.setTelephone(client.getTelephone());
    existing.setNumPermis(client.getNumPermis());
    existing.setDateInscription(client.getDateInscription());
    return clientRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new ResourceNotFoundException("Client", id);
    }
    clientRepository.deleteById(id);
  }

  @Override
  public Client saveClient(Client client) {
    return create(client);
  }

  @Override
  public Optional<Client> getClientById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Client> getAllClients() {
    return findAll();
  }

  @Override
  public void deleteClient(Long id) {
    deleteById(id);
  }

  private void validateClient(Client client) {
    if (client == null) {
      throw new IllegalArgumentException("Le client ne peut pas être nul");
    }
    if (client.getNom() == null || client.getNom().isBlank()) {
      throw new IllegalArgumentException("Le nom du client est obligatoire");
    }
    if (client.getPrenom() == null || client.getPrenom().isBlank()) {
      throw new IllegalArgumentException("Le prénom du client est obligatoire");
    }
    if (client.getEmail() == null || client.getEmail().isBlank()) {
      throw new IllegalArgumentException("L'email du client est obligatoire");
    }
    if (client.getTelephone() == null || client.getTelephone().isBlank()) {
      throw new IllegalArgumentException("Le téléphone du client est obligatoire");
    }
    if (client.getNumPermis() == null || client.getNumPermis().isBlank()) {
      throw new IllegalArgumentException("Le numéro de permis du client est obligatoire");
    }
  }
}
