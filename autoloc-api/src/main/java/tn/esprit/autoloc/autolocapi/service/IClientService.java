package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.autolocapi.domain.Client;

public interface IClientService {
  Client create(Client client);

  Client findById(Long id);

  List<Client> findAll();

  Client update(Long id, Client client);

  void deleteById(Long id);

  default Client saveClient(Client client) {
    return create(client);
  }

  default Optional<Client> getClientById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  default List<Client> getAllClients() {
    return findAll();
  }

  default void deleteClient(Long id) {
    deleteById(id);
  }
}
