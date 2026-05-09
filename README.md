# Root folder of TODO app

This is a simple front + server application for keeping track of TODO items.

## Folder todoapp
A simple single page react application for creating, marking as done and deleting TODO items.

## Folder server
A simple Spring Boot application working as a simple server between the front and the database. It offers simple HTTP endpoints for creating, reading, updating and deleting TODO items.

Note, because this is a simple app, the endpoints are unauthenticated and use unsecure protocol (HTTP). 

## Running whole stack 

Folder named docker contains two docker compose files for easy running of the system.

### docker/docker-compose-pg.yaml
Docker compose file for running a Postgresql 18.3 server. Default PostgreSQK port 5432 is exposed. The service name is tododb.

### docker/docker-compose-app.yaml
Docker compose file for running both backend and frontend, services named todoback and todoapp. 

Backend has been configured to use the postgreSQL from the docker/docker-compose-pg.yaml file using docker's internal hostname of the database. Port 8080 is exposed.

Frontend has been configured to use the backend from this file's backend, todoback, using the docker's internal hostname of the backend. Port 80 is exposed.

### Run examples
All the examples expect one to be in the docker folder
```bash
cd docker
```

If one wants to simply run it all:
```bash
docker compose -f docker-compose-pg.yaml -f docker-compose-app.yaml up
```

After code changes, remember to rebuild the containers
If one wants to simply run it all:
```bash
docker compose -f docker-compose-pg.yaml -f docker-compose-app.yaml up --build
```

Partial running during development is possible, too. E.g. running only database and backend from docker compose and frontend locally:
```bash
docker compose -f docker-compose-pg.yaml -f docker-compose-app.yaml up tododb todoback
```
and check todoapp's README.md to see how to run frontend locally.
