package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.repository.IPaiementRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

  private final IPaiementRepository paiementRepository;

  @Override
  public Paiement findById(Long id) {
    return paiementRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
  }

  @Override
  public List<Paiement> findAll() {
    return paiementRepository.findAll();
  }

  @Override
  public Optional<Paiement> getPaiementById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Paiement> getAllPaiements() {
    return findAll();
  }
}
