package tn.esprit.autoloc.autolocapi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

  private final IContratRepository contratRepository;

  @Override
  public Contrat create(Contrat contrat) {
    validateContrat(contrat);
    if (contrat.getIdContrat() != null) {
      throw new IllegalArgumentException("L'identifiant du contrat doit être null pour une création");
    }
    return contratRepository.save(contrat);
  }

  @Override
  public Contrat findById(Long id) {
    return contratRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
  }

  @Override
  public List<Contrat> findAll() {
    return contratRepository.findAll();
  }

  @Override
  public Contrat update(Long id, Contrat contrat) {
    Contrat existing = findById(id);
    validateContrat(contrat);
    existing.setDateSignature(contrat.getDateSignature());
    existing.setMontantTotal(contrat.getMontantTotal());
    existing.setValide(contrat.isValide());
    existing.setReservation(contrat.getReservation());
    return contratRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    if (!contratRepository.existsById(id)) {
      throw new ResourceNotFoundException("Contrat", id);
    }
    contratRepository.deleteById(id);
  }

  @Override
  public Contrat saveContrat(Contrat contrat) {
    return create(contrat);
  }

  @Override
  public Optional<Contrat> getContratById(Long id) {
    return Optional.ofNullable(findById(id));
  }

  @Override
  public List<Contrat> getAllContrats() {
    return findAll();
  }

  @Override
  public void deleteContrat(Long id) {
    deleteById(id);
  }

  private void validateContrat(Contrat contrat) {
    if (contrat == null) {
      throw new IllegalArgumentException("Le contrat ne peut pas être nul");
    }
    if (contrat.getMontantTotal() != null && contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Le montant total du contrat ne peut pas être négatif");
    }
  }
}
