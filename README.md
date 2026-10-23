# AutoLoc

## Documentation de l'API

L'API expose les opérations CRUD pour les véhicules, les clients et les agences.
Après le démarrage de `autoloc-api`, la documentation interactive est disponible
sur [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html)
et le document OpenAPI sur `http://localhost:8000/v3/api-docs`.

Depuis le dossier du projet, démarrez l'API avec Java 21 installé et MySQL configuré
conformément à `autoloc-api/src/main/resources/application.properties` :

```powershell
Set-Location .\autoloc-api
.\mvnw.cmd spring-boot:run
```

Pour valider les routes en classe, importer
[`autoloc-api/postman/AutoLoc-CRUD.postman_collection.json`](./autoloc-api/postman/AutoLoc-CRUD.postman_collection.json)
dans Postman, démarrer l'API et lancer la collection dans le Runner. La collection
utilise `http://localhost:8000` par défaut et conserve les identifiants retournés
par les requêtes de création pour les opérations de lecture, modification et suppression.