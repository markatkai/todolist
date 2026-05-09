# Todo app's server
This is the backend for todo application. It provides simple HTTP endpoints for creating, reading, updating and deleting todo items.
The endpoints are not secured (HTTPS) and there is no authentication to them. They are very dummy endpoints.

## APIs


## Development

### Building
One can build a jar of the server e.g by running:
```bash
mvn clean package
```

### Running the server on its own
Before running the server, one needs to set the environment variables needed for connecting the database.
There is a .env.template that contains all the environment variables that need to be set.
One can set the environment variables e.g. by copying the template to .env file, changing real values and running the following:

```bash
set -a
source ./.env
set +a
```

```bash
mvn spring-boot:run
```
The server will run in port 8080.

## Running with docker compose

Whole the stack can be started with the docker compose files in the ../docker folder. 
Dockerfile builds the jar and runs it. Port 8080 for the server is exposed.
