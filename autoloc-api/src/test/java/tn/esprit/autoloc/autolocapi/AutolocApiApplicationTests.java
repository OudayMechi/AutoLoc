package tn.esprit.autoloc.autolocapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AutolocApiApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void contextLoads() {
  }

  @Test
  void createAgenceReturnsDtoAndCanDeleteIt() throws Exception {
    MvcResult result = mockMvc.perform(post("/agences")
            .contentType("application/json")
            .content("""
                {
                  "nom": "Agence DTO test",
                  "ville": "Tunis",
                  "adresse": "1 rue de test",
                  "telephone": "20123456"
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.idAgence").isNumber())
        .andExpect(jsonPath("$.nom").value("Agence DTO test"))
        .andExpect(jsonPath("$.employes").doesNotExist())
        .andReturn();

    Matcher idMatcher = Pattern.compile("\"idAgence\"\\s*:\\s*(\\d+)")
        .matcher(result.getResponse().getContentAsString());
    if (!idMatcher.find()) {
      throw new AssertionError("L'identifiant de l'agence doit figurer dans le DTO retourné");
    }
    mockMvc.perform(delete("/agences/{id}", Long.parseLong(idMatcher.group(1))))
        .andExpect(status().isNoContent());
  }

  @Test
  void invalidClientEmailReturnsCentralizedValidationErrors() throws Exception {
    mockMvc.perform(post("/clients")
            .contentType("application/json")
            .content("""
                {
                  "nom": "Test",
                  "prenom": "Client",
                  "email": "adresse-invalide",
                  "telephone": "20123456",
                  "numPermis": "PERMIS-TEST"
                }
                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.erreurs.email").exists());
  }

  @Test
  void allRequestDtosRejectInvalidFields() throws Exception {
    List<InvalidPayload> invalidPayloads = List.of(
        new InvalidPayload("/agences", """
            {"nom":"","ville":"Tunis","adresse":"1 rue de test","telephone":"20123456"}
            """),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"","adresse":"1 rue de test","telephone":"20123456"}
            """),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"Tunis","adresse":"","telephone":"20123456"}
            """),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"Tunis","adresse":"1 rue de test","telephone":""}
            """),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"Tunis","adresse":"1 rue de test","telephone":"telephone"}
            """),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"Tunis","adresse":"1 rue de test","telephone":"------"}
            """),
        new InvalidPayload("/agences", """
            {"nom":"%s","ville":"Tunis","adresse":"1 rue de test","telephone":"20123456"}
            """.formatted("n".repeat(101))),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"%s","adresse":"1 rue de test","telephone":"20123456"}
            """.formatted("v".repeat(51))),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"Tunis","adresse":"%s","telephone":"20123456"}
            """.formatted("a".repeat(151))),
        new InvalidPayload("/agences", """
            {"nom":"Agence","ville":"Tunis","adresse":"1 rue de test","telephone":"%s"}
            """.formatted("2".repeat(21))),
        new InvalidPayload("/clients", """
            {"nom":"","prenom":"Client","email":"client@example.com","telephone":"20123456","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"","email":"client@example.com","telephone":"20123456","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"","telephone":"20123456","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"pas-un-email","telephone":"20123456","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"client@example.com","telephone":"","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"client@example.com","telephone":"telephone","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"client@example.com","telephone":"------","numPermis":"PERMIS-1"}
            """),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"client@example.com","telephone":"20123456","numPermis":""}
            """),
        new InvalidPayload("/clients", """
            {"nom":"%s","prenom":"Client","email":"client@example.com","telephone":"20123456","numPermis":"PERMIS-1"}
            """.formatted("n".repeat(51))),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"%s","email":"client@example.com","telephone":"20123456","numPermis":"PERMIS-1"}
            """.formatted("p".repeat(51))),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"%s@example.com","telephone":"20123456","numPermis":"PERMIS-1"}
            """.formatted("e".repeat(90))),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"client@example.com","telephone":"%s","numPermis":"PERMIS-1"}
            """.formatted("2".repeat(21))),
        new InvalidPayload("/clients", """
            {"nom":"Nom","prenom":"Client","email":"client@example.com","telephone":"20123456","numPermis":"%s"}
            """.formatted("P".repeat(51))),
        new InvalidPayload("/employes", """
            {"nom":"","prenom":"Test","role":"AGENT"}
            """),
        new InvalidPayload("/employes", """
            {"nom":"Test","prenom":"","role":"AGENT"}
            """),
        new InvalidPayload("/employes", """
            {"nom":"Test","prenom":"Employe","role":null}
            """),
        new InvalidPayload("/employes", """
            {"nom":"Test","prenom":"Employe","role":"AGENT","agence":{"idAgence":0}}
            """),
        new InvalidPayload("/employes", """
            {"nom":"Test","prenom":"Employe","role":"AGENT","agence":{"idAgence":null}}
            """),
        new InvalidPayload("/employes", """
            {"nom":"%s","prenom":"Employe","role":"AGENT"}
            """.formatted("n".repeat(51))),
        new InvalidPayload("/employes", """
            {"nom":"Employe","prenom":"%s","role":"AGENT"}
            """.formatted("p".repeat(51))),
        new InvalidPayload("/equipements", """
            {"libelle":"","quantite":1}
            """),
        new InvalidPayload("/equipements", """
            {"libelle":"%s","quantite":1}
            """.formatted("e".repeat(101))),
        new InvalidPayload("/equipements", """
            {"libelle":"Equipement","quantite":-1}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"%s","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """.formatted("I".repeat(21))),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"%s","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """.formatted("m".repeat(51))),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"%s","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """.formatted("m".repeat(51))),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":null,
            "tarifJournalier":50,"statut":"DISPONIBLE"}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":null,"statut":"DISPONIBLE"}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":-1,"statut":"DISPONIBLE"}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":null}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE","agence":{"idAgence":0}}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE","agence":{"idAgence":null}}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE","equipements":[{"idEquipement":0}]}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE","equipements":[{"idEquipement":null}]}
            """),
        new InvalidPayload("/vehicules", """
            {"immatriculation":"TEST-1","marque":"Test","modele":"Test","categorie":"CITADINE",
            "tarifJournalier":50,"statut":"DISPONIBLE","equipements":[null]}
            """),
        new InvalidPayload("/maintenances", """
            {"dateDebut":null}
            """),
        new InvalidPayload("/maintenances", """
            {"dateDebut":"2026-01-01","cout":-1}
            """),
        new InvalidPayload("/maintenances", """
            {"dateDebut":"2026-01-01","description":"%s"}
            """.formatted("x".repeat(256))),
        new InvalidPayload("/maintenances", """
            {"dateDebut":"2026-01-01","vehicule":{"idVehicule":0}}
            """),
        new InvalidPayload("/maintenances", """
            {"dateDebut":"2026-01-01","vehicule":{"idVehicule":null}}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":null,"dateFin":"2026-12-10","statut":"CONFIRMEE"}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":"2026-12-10","dateFin":null,"statut":"CONFIRMEE"}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":"2026-12-10","dateFin":"2026-12-11","statut":null}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":"2026-12-10","dateFin":"2026-12-11","statut":"CONFIRMEE",
            "vehicule":{"idVehicule":0}}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":"2026-12-10","dateFin":"2026-12-11","statut":"CONFIRMEE",
            "vehicule":{"idVehicule":null}}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":"2026-12-10","dateFin":"2026-12-11","statut":"CONFIRMEE",
            "client":{"idClient":0}}
            """),
        new InvalidPayload("/reservations", """
            {"dateDebut":"2026-12-10","dateFin":"2026-12-11","statut":"CONFIRMEE",
            "client":{"idClient":null}}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":null,"montantTotal":100,"valide":true}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":null,"valide":true}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":-1,"valide":true}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":100,"valide":null}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":100,"valide":true,
            "reservation":{"idReservation":0}}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":100,"valide":true,
            "reservation":{"idReservation":null}}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":100,"valide":true,
            "paiements":[{"montant":null,"datePaiement":"2026-01-01","modePaiement":"CARTE"}]}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":100,"valide":true,
            "paiements":[{"montant":0,"datePaiement":null,"modePaiement":null}]}
            """),
        new InvalidPayload("/contrats", """
            {"dateSignature":"2026-01-01","montantTotal":100,"valide":true,"paiements":[null]}
            """));

    for (InvalidPayload payload : invalidPayloads) {
      mockMvc.perform(post(payload.path())
              .contentType("application/json")
              .content(payload.body()))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.erreurs").isNotEmpty());
    }
  }

  @Test
  void invalidDateRangesReturnBadRequest() throws Exception {
    mockMvc.perform(post("/reservations")
            .contentType("application/json")
            .content("""
                {"dateDebut":"2026-12-11","dateFin":"2026-12-10","statut":"CONFIRMEE"}
                """))
        .andExpect(status().isBadRequest());

    mockMvc.perform(post("/maintenances")
            .contentType("application/json")
            .content("""
                {"dateDebut":"2026-12-11","dateFin":"2026-12-10"}
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void invalidEnumAndDateFormatsReturnCentralizedBadRequest() throws Exception {
    mockMvc.perform(post("/reservations")
            .contentType("application/json")
            .content("""
                {"dateDebut":"2026-12-10","dateFin":"2026-12-11","statut":"INCONNU"}
                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.message").value("Le corps JSON est invalide ou incomplet"));

    mockMvc.perform(post("/reservations")
            .contentType("application/json")
            .content("""
                {"dateDebut":"pas-une-date","dateFin":"2026-12-11","statut":"CONFIRMEE"}
                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.message").value("Le corps JSON est invalide ou incomplet"));
  }

  private record InvalidPayload(String path, String body) {
  }
}
