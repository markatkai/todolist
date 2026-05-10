# Todo app's server
This is the backend for todo application. It provides simple HTTP endpoints for creating, reading, updating and deleting todo items.
The endpoints are not secured (HTTPS) and there is no authentication to them. They are very dummy endpoints.

## APIs

### GET /notes

Find non deleted notes.
The following optional query parameters exist for the endpoint:
- timeStart - find notes created at timeStart or later. Format is ISO 8601 format
- timeEnd - find notes created before timeEnd. Format is ISO 8601 format
- status - find notes with the exact status. The accepted statuses are FINISHED or UNFINISHED
- orderBy - order the result by the given value. The accepted values are createTime, finishingTime or status
- order - if orderBy is given, order the values in ascending or descending order. The accepted values are ASC and DESC

### POST /notes

Create a new note. The format for the request body is e.g.
```json
{
    "text":"the text for the note"
}
```
Returns full data for the new note. The response body is e.g.
```json
{
    "id": 23,
    "createTime": "2026-05-10T17:40:02.517909233Z",
    "text": "the text for the note",
    "status": "UNFINISHED",
    "finishingTime": null
}
```

### PUT /notes/{id}

Update the status of a note. The format for the request body is e.g.
```json
{
    "status":"FINISHED"
}
```
Returns full updated data for the updated note. The response body is e.g.
```json
{
    "id": 23,
    "createTime": "2026-05-10T17:40:02.517909233Z",
    "text": "the text for the note",
    "status": "FINISHED",
    "finishingTime": "2026-05-10T17:42:02.517909233Z"
}
```

### DELETE /notes/{id}

Mark the given value as deleted.

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
