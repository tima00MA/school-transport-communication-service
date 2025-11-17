# Async Communication Service

## Description
Microservice centralisant les communications synchrones (REST) et asynchrones (RabbitMQ) pour l'application de transport scolaire.

## Fonctionnalités
- Routage synchrones : /sync/{targetService}/{path}
- Publication asynchrones : /async/publish
- Abonnements : /async/subscribe
- Logs : /async/logs

## Événements Asynchrones
- bus.updated
- student.created
- groups.created
- eta.updated

## Installation
1. Compiler : mvn clean install
2. Lancer : java -jar target/async-communication-service-0.0.1-SNAPSHOT.jar
3. Dépendances : PostgreSQL, RabbitMQ, Eureka

## Docker
docker build -t async-communication-service .
docker run -p 3009:3009 async-communication-service

## Configuration
- application.yml : DB, RabbitMQ, Eureka
- bootstrap.yml : Spring Cloud Config

## Tests
mvn test