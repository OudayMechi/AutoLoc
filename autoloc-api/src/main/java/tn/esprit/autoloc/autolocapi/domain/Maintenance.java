package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Maintenance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idMaintenance;

  @Column(nullable = false)
  private LocalDate dateDebut;

  private LocalDate dateFin;

  @Column(length = 255)
  private String description;

  @Column(precision = 10, scale = 2)
  private BigDecimal cout;

  @ManyToOne
  private Vehicule vehicule;
}
