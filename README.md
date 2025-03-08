# Migration de Données MySQL vers PostgreSQL avec Spring Batch

## Introduction

La migration de données entre bases de données est souvent nécessaire lors de la mise à l'échelle d'applications ou de la transition vers une solution plus performante. Ce projet gère la migration de MySQL vers PostgreSQL, en tenant compte des différences telles que les types `BOOLEAN`, les colonnes `SERIAL` et la gestion des `ENUM`. Il assure la migration des utilisateurs, de leurs publications et de leurs commentaires.

## Technologies Utilisées

* **Spring Boot** : Framework backend pour la construction d'applications autonomes.
* **Spring Batch** : Traitement par lots pour les grands ensembles de données.
* **MySQL** : Base de données source.
* **PostgreSQL** : Base de données cible. 
* **Lombok** : Réduction du code boilerplate pour les entités et les services.

## Fonctionnement

Le projet utilise **Spring Batch** pour diviser le processus de migration en plusieurs étapes :

* **Étape categories** : Migration des données utilisateur de MySQL vers PostgreSQL avec la verification de l existence des données (doublons). 
* **Étape films** : Migration des films avec leurs liens avec le type de categorie avec une vérification des doublons

L'ordre d'exécution respecte les contraintes d'intégrité des données, garantissant que les films sont migrés apres la migration des données des catégories.

## Instructions de Configuration

1.  **Cloner le dépôt** :

    ```bash
    git clone https://github.com/faresDev345/spring-boot-data-entry.git
    cd spring-batch-movie-import
    ```

2.  **Configurer l'environnement** :
    Créer un fichier `.env` avec les mêmes entrées que `.env.example` :

    ```bash
    MYSQL_URL=jdbc:mysql://localhost:3306/mydb
    MYSQL_USERNAME=utilisateur
    MYSQL_PASSWORD=motdepasse
    MYSQL_DATABASE=basededonnees

    POSTGRES_URL=jdbc:postgresql://localhost:5432/mydb
    POSTGRES_USERNAME=postgres
    POSTGRES_PASSWORD=motdepasse
    POSTGRES_DATABASE=basededonnees
    ```

3.  **Compiler le projet** :

    ```bash
    ./mvnw clean install
    ```

4.  **Exécuter l'application** :

    ```bash
    ./mvnw spring-boot:run
    ```

## Exécution du Job Batch

Vous pouvez démarrer le job batch de migration de données via le point de terminaison REST suivant :
