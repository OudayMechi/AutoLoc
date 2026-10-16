package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Agence {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idAgence;

  @Column(nullable = false, length = 100)
  private String nom;

  @Column(nullable = false, length = 50)
  private String ville;

  @Column(nullable = false, length = 150)
  private String adresse;

  @Column(nullable = false, length = 20)
  private String telephone;

  @OneToMany(mappedBy = "agence")
  private List<Employe> employes;

  @OneToMany(mappedBy = "agence")
  private List<Vehicule> vehicules;
}
