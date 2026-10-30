# Atelier 5 — AutoLoc API

## A. Endpoints

Base URL locale : `http://localhost:8080/api`. Toutes les opérations passent par l'interface de service indiquée; les contrôleurs n'utilisent pas les repositories.

| Contrôleur | Verbe et route | Service appelé | Réponses documentées |
|---|---|---|---|
| AgenceController | `GET /agences`, `GET /agences/{id}` | `IAgenceService.findAll`, `findById` | 200, 404 pour l'identifiant |
| AgenceController | `POST /agences`, `PUT /agences/{id}`, `DELETE /agences/{id}` | `IAgenceService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| EmployeController | `GET /employes`, `GET /employes/{id}` | `IEmployeService.findAll`, `findById` | 200, 404 pour l'identifiant |
| EmployeController | `POST /employes`, `PUT /employes/{id}`, `DELETE /employes/{id}` | `IEmployeService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| VehiculeController | `GET /vehicules`, `GET /vehicules/{id}` | `IVehiculeService.findAll`, `findById` | 200, 404 pour l'identifiant |
| VehiculeController | `POST /vehicules`, `PUT /vehicules/{id}`, `DELETE /vehicules/{id}` | `IVehiculeService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| EquipementController | `GET /equipements`, `GET /equipements/{id}` | `IEquipementService.findAll`, `findById` | 200, 404 pour l'identifiant |
| EquipementController | `POST /equipements`, `PUT /equipements/{id}`, `DELETE /equipements/{id}` | `IEquipementService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| ClientController | `GET /clients`, `GET /clients/{id}` | `IClientService.findAll`, `findById` | 200, 404 pour l'identifiant |
| ClientController | `POST /clients`, `PUT /clients/{id}`, `DELETE /clients/{id}` | `IClientService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| ReservationController | `GET /reservations`, `GET /reservations/{id}` | `IReservationService.findAll`, `findById` | 200, 404 pour l'identifiant |
| ReservationController | `POST /reservations`, `PUT /reservations/{id}`, `DELETE /reservations/{id}` | `IReservationService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| MaintenanceController | `GET /maintenances`, `GET /maintenances/{id}` | `IMaintenanceService.findAll`, `findById` | 200, 404 pour l'identifiant |
| MaintenanceController | `POST /maintenances`, `PUT /maintenances/{id}`, `DELETE /maintenances/{id}` | `IMaintenanceService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| ContratController | `GET /contrats`, `GET /contrats/{id}` | `IContratService.findAll`, `findById` | 200, 404 pour l'identifiant |
| ContratController | `POST /contrats`, `PUT /contrats/{id}`, `DELETE /contrats/{id}` | `IContratService.create`, `update`, `deleteById` | 201/400, 200/400/404, 204/404 |
| PaiementController | `GET /paiements`, `GET /paiements/{id}` | `IPaiementService.findAll`, `findById` | 200, 404 pour l'identifiant |

Paiement est volontairement en lecture seule; `POST /api/paiements` n'est pas mappé et doit produire 405.

Tous les endpoints de lecture, création et modification exposent des DTO de réponse (`ApiDtos`) et non des entités JPA. Les `ApiRequestDto` portent les contraintes Jakarta Validation et les références aux associations sont exprimées par leurs identifiants. `ApiMapper` convertit les requêtes DTO en entités pour les services, puis leurs résultats en DTO de réponse.

Les contrôles exécutés à l'entrée des routes POST et PUT couvrent tous les DTO :

| Ressource | Contrôles de saisie |
|---|---|
| Agence | Nom, ville et adresse obligatoires et limités respectivement à 100, 50 et 150 caractères; téléphone obligatoire, limité à 20 caractères, composé de caractères téléphoniques autorisés et contenant au moins un chiffre. |
| Client | Nom, prénom, e-mail, téléphone et numéro de permis obligatoires; tailles maximales 50, 50, 100, 20 et 50; e-mail valide et téléphone au format contrôlé. |
| Employé | Nom, prénom et rôle obligatoires; noms limités à 50 caractères; référence agence optionnelle, mais son identifiant doit être positif s'il est fourni. |
| Équipement | Libellé obligatoire et limité à 100 caractères; quantité optionnelle, mais non négative. |
| Véhicule | Immatriculation, marque, modèle, catégorie, tarif journalier et statut obligatoires; tailles maximales 20, 50 et 50; tarif non négatif; références agence et équipements optionnelles, avec identifiants positifs et éléments de liste non nuls. |
| Maintenance | Date de début obligatoire; date de fin optionnelle; description limitée à 255 caractères; coût optionnel et non négatif; référence véhicule optionnelle avec identifiant positif. Si une date de fin est fournie, elle ne peut précéder la date de début. |
| Réservation | Dates de début et de fin et statut obligatoires; date de fin postérieure à la date de début; références véhicule/client optionnelles avec identifiants positifs. |
| Contrat | Date de signature, montant total et indicateur valide obligatoires; montant total non négatif; réservation optionnelle avec identifiant positif; paiements optionnels dont les éléments ne peuvent être nuls. |
| Paiement dans un contrat | Montant au moins égal à 0,01, date et mode de paiement obligatoires. Le paiement n'ayant pas de route de création propre, ces contrôles sont appliqués aux paiements reçus dans la requête de contrat. |

Les références imbriquées sont validées en cascade. Les valeurs d'énumération ou dates qui ne peuvent pas être lues depuis le JSON sont également rejetées. `GlobalExceptionHandler` centralise les erreurs : les violations de contraintes renvoient 400 avec le détail des champs et les erreurs de format JSON renvoient 400 avec un message commun. Les tests d'intégration couvrent les champs requis, limites de longueur, formats, montants, quantités, références imbriquées, listes, formats JSON et cohérence des dates. Pour les exécuter depuis la racine du dépôt sous Windows avec Java 21 et MySQL configuré :

```powershell
$env:JAVA_HOME = "C:\chemin\vers\Java 21"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\autoloc-api\mvnw.cmd -f .\autoloc-api\pom.xml test
```

Les validations de quantité d'équipement et de coût de maintenance demandées dans le TP nécessitaient ces deux propriétés, absentes du modèle de départ. `Equipement.quantite` et `Maintenance.cout` sont ajoutées sans imposer une contrainte obligatoire supplémentaire; une valeur négative est rejetée avec 400.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| `Contrat.paiements` ↔ `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | Sérialise les paiements depuis le contrat sans reboucler sur leur contrat. La désérialisation du contrat rétablit le côté parent. Le mapping JPA utilise `CascadeType.ALL` et `orphanRemoval=true` pour propager persistance et suppression. |
| `Agence.employes` et `Agence.vehicules` | `@JsonIgnore` sur les collections | Évite de repartir de chaque employé/véhicule vers l'agence et ses collections. |
| `Client.reservations` | `@JsonIgnore` sur la collection | Évite le cycle Client–Reservation tout en laissant `Reservation.client` désérialisable lors d'un POST. |
| `Vehicule.maintenances` et `Vehicule.reservations` | `@JsonIgnore` sur les collections | Évite les cycles par les références `vehicule` des objets associés. |
| `Equipement.vehicules` | `@JsonIgnore` sur la collection inverse | Évite le cycle de la relation plusieurs-à-plusieurs. |
| `Reservation.contrat` | `@JsonIgnore` | Évite le cycle avec `Contrat.reservation`; `Reservation.client` reste inchangé. |

Ces annotations Jackson restent utiles aux entités JPA et à leurs relations internes, mais ne définissent plus la forme des réponses publiques : les contrôleurs sérialisent les DTO, dont les associations sont volontairement limitées à des objets de réponse dédiés afin d'éviter les cycles et les champs inutiles. Le JSON d'erreur commun contient `status`, `message`, `horodatage` et `erreurs`; une validation invalide renvoie 400 avec le détail des champs, une ressource absente renvoie 404 et une collision de données renvoie 409.

## C. Postman et Swagger

Postman sert à exécuter des requêtes HTTP répétables et à vérifier automatiquement les statuts attendus. Importer l'environnement `autoloc-api/postman/AutoLoc-local.postman_environment.json` (nom `local`, variable `baseUrl = http://localhost:8080`) et la collection `autoloc-api/postman/AutoLoc-Atelier5.postman_collection.json`; sélectionner l'environnement `local`. La collection enregistre les identifiants créés et inclut les cas CRUD, 404, 400 pertinents, la cascade du contrat et le POST paiement attendu en 405. Exécuter les requêtes dans l'ordre et le dossier de suppression en dernier.

Swagger UI sert à explorer les routes et les schémas, puis à essayer les opérations depuis le navigateur. Après démarrage, consulter `http://localhost:8080/api/swagger-ui.html`; le document OpenAPI est à `http://localhost:8080/api/v3/api-docs`. Le projet utilise Spring Boot 4.1.1 et SpringDoc 3.1.1, dont le POM publié hérite de Spring Boot 4.1.0; cette version déjà déclarée est conservée car elle correspond à la ligne Spring Boot 4.1 utilisée par le projet. La configuration conserve le port 8080 du TP et utilise `spring.jpa.hibernate.ddl-auto=update` pour éviter que l'arrêt de l'application supprime les tables. Pendant la vérification, le port 8080 était occupé; les tests locaux de Swagger ont donc été faits sur 8081.

Exemple concret : envoyer le POST `/api/contrats` dans Postman avec un contrat contenant deux objets `paiements` (montant, datePaiement, modePaiement) et vérifier 201, puis envoyer GET `/api/paiements` pour voir les paiements créés en cascade. Après DELETE du contrat, la collection vérifie que chacun de ses paiements répond 404.

Captures d'exécution enregistrées dans `docs/captures/` : `swagger-endpoints.png` montre les routes documentées dans Swagger UI et `validation-400.png` montre les réponses réelles de lecture DTO (200) et de validation (400). CoolRequest reste disponible pour envoyer les mêmes requêtes depuis IntelliJ.

## D. Anomalies SonarQube

**L'analyse SonarQube n'a pas été exécutée dans cet environnement et aucun rapport Sonar n'est disponible. Les éléments ci-dessous sont des anomalies fonctionnelles constatées dans le code source avant correction, pas des résultats SonarQube :**

1. Les contrôleurs préfixaient déjà leurs routes par `/api`, tandis que les routes attendues utilisent le context-path `/api`. La configuration n'avait pas de context-path. Le context-path a été configuré et le préfixe retiré des `@RequestMapping`.
2. `GlobalExceptionHandler` et `ResourceNotFoundException` sont dans `tn.esprit.autoloc.exception`, hors du sous-package de l'application principale `tn.esprit.autoloc.autolocapi`. Le gestionnaire n'était donc pas inclus dans le scan de composants par défaut. Le scan de composants a été élargi à `tn.esprit.autoloc` et les erreurs ont un corps JSON commun avec le code, le message et l'horodatage.

Ces observations ne doivent pas être présentées comme des bugs détectés par SonarQube. Une analyse Sonar effective reste à lancer pour compléter cette partie avec les issues réellement rapportées.

## E. Questions de compréhension — page 11

1. **Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode contrôleur à appeler ?** Le `DispatcherServlet` reçoit les requêtes HTTP. Spring MVC consulte les mappings déclarés par les annotations (`@RequestMapping`, `@GetMapping`, etc.) et choisit le contrôleur et la méthode correspondant au chemin et au verbe HTTP.
2. **Pourquoi `ContratController` dépend-il de `IContratService` plutôt que de `ContratRepository` ou `ContratServiceImpl` ?** Le contrôleur délègue les cas d'usage à l'interface de service, ce qui sépare la couche web de la persistance, conserve les règles métier dans le service et facilite le remplacement ou le test de l'implémentation.
3. **Pourquoi POST renvoie-t-il 201 et DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?** 201 indique qu'une ressource a été créée; 204 indique que la suppression a réussi sans corps de réponse. Une ressource absente est une situation HTTP connue transformée par `ResourceNotFoundException` en 404; 500 est réservé à une erreur interne non prévue.
4. **Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit POST `/api/paiements` ?** La gestion des paiements est en lecture seule dans cet atelier; la création est effectuée lors de la création d'un contrat et propagée par cascade JPA. Aucun mapping POST n'existe, donc Spring MVC répond 405 Method Not Allowed.
5. **Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?** Une entité exposée directement couple l'API au schéma interne, peut divulguer des champs, charger des relations paresseuses et créer des cycles JSON. `Contrat.paiements` doit être sérialisé et créé dans le corps du contrat pour démontrer la composition et la cascade, donc il utilise une paire `@JsonManagedReference`/`@JsonBackReference`. Les véhicules d'une agence ne sont pas requis dans ce corps et la relation inverse peut boucler; `@JsonIgnore` exclut simplement cette collection.
