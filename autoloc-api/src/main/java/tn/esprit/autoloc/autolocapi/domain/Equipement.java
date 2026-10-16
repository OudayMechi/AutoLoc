package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "equipement")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Equipement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idEquipement;

  @Column(nullable = false, length = 100)
  private String libelle;

  @ManyToMany(mappedBy = "equipements")
  private List<Vehicule> vehicules;
}
