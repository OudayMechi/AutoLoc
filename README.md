# AutoLoc

## Documentation de l'API

L'API REST expose les opérations de gestion des agences, clients, employés,
véhicules, équipements, réservations, maintenances, contrats et paiements. Les
réponses HTTP sont converties en DTO et les données reçues sont validées avant
leur transmission aux services.

Après le démarrage de `autoloc-api`, la documentation interactive est disponible
sur [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)
et le document OpenAPI sur `http://localhost:8080/api/v3/api-docs`.

Depuis le dossier du dépôt, démarrez l'API avec Java 21 et MySQL configuré
conformément à `autoloc-api/src/main/resources/application.properties` :

```powershell
Set-Location .\autoloc-api
.\mvnw.cmd spring-boot:run
```

Les erreurs de validation retournent le statut HTTP 400 et une réponse JSON
contenant le statut, un message et les champs invalides. Par exemple, un client
doit fournir une adresse électronique valide et un numéro de permis.