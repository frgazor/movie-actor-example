# Movie Actor API

This project is a sample Java REST API for managing movie and actor records. It exposes JSON endpoints under `/api` and stores data using JPA/Hibernate, with H2 configured for local development and testing. The application is built as a Java EE-style WAR and can be deployed to a servlet container such as GlassFish or run through the provided Docker setup.

## Features

- Create, read, update, and delete movie records
- Create, read, update, and delete actor records
- Link actors to movies and remove those relationships
- Paginated listing endpoints with metadata headers
- ETag-based caching for GET responses
- Actor picture retrieval via image endpoint
- Test-data insertion endpoints for demo data

## Tech Stack

- Java 8
- Maven
- JAX-RS / Jersey
- Hibernate ORM
- H2 database
- GlassFish 5 (Docker deployment target)
- Docker Compose

## Project Structure

```text
.
├── database/
│   ├── db.txt                 # SQL schema for movies, actors, and sequences
│   └── moviedb/               # database-related assets
├── docker/
│   ├── Dockerfile             # GlassFish deployment image setup
│   └── docker-compose.yml     # container orchestration for GlassFish + H2
├── src/
│   ├── main/
│   │   ├── resources/
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── web.xml    # Jersey servlet registration
│   │           └── glassfish-web.xml
│   └── si/
│       └── cornholio/
│           └── movieInfo/
│               ├── MovieActorApplication.java
│               ├── MovieResource.java
│               ├── ActorResource.java
│               ├── Movie.java
│               ├── Actor.java
│               ├── MovieDAO.java
│               ├── ActorDAO.java
│               ├── CustomMovieIdGenerator.java
│               ├── CustomActorIdGenerator.java
│               └── ...
├── test/
│   └── ...
├── pom.xml
├── .gitignore
└── README.md
```

## Application Entry Point

The application registers a Jersey servlet mapped to `/api/*` in `src/main/webapp/WEB-INF/web.xml` and sets the application class in `MovieActorApplication.java`.

```java
@ApplicationPath("/api")
public class MovieActorApplication extends Application {
}
```

This means all REST endpoints are served under the `/api` path.

## REST API

### Movies

Base path: `/api/movies`

- `GET /api/movies/list?page=1&size=10`  -> list movies with pagination metadata
- `GET /api/movies/{id}` -> fetch a movie by ID
- `POST /api/movies` -> create a movie
- `PUT /api/movies/{id}` -> update a movie
- `DELETE /api/movies/{id}` -> delete a movie
- `POST /api/movies/{movieId}/actors/{actorId}` -> associate an actor with a movie
- `DELETE /api/movies/{movieId}/actors/{actorId}` -> remove actor from a movie
- `POST /api/movies/insertTestData` -> insert sample movie data

### Actors

Base path: `/api/actors`

- `GET /api/actors?page=1&size=10` -> list actors with pagination metadata
- `GET /api/actors/{id}` -> fetch an actor by ID
- `POST /api/actors` -> create an actor
- `PUT /api/actors/{id}` -> update an actor
- `DELETE /api/actors/{id}` -> delete an actor
- `GET /api/actors/{id}/picture` -> fetch actor photo if present
- `POST /api/actors/insertTestData` -> insert sample actor data

## Data Model

The application uses two main entities:

- `Movie`
  - id
  - title
  - genre
  - movieYear
  - description
  - actors relationship

- `Actor`
  - id
  - firstName
  - lastName
  - dateOfBirth
  - picture (binary/blob)

The schema in `database/db.txt` defines tables for:

- `movie`
- `actor`
- `movie_actor`
- sequences for generating IDs

## Build and Run

### Prerequisites

- Java 8+
- Maven
- Docker + Docker Compose (optional, for containerized run)

### Run locally with Maven

From the project root:

```bash
mvn clean package
```

Then deploy the generated WAR to a servlet container such as GlassFish or Tomcat.

You can also run the app in a local Java EE container if your environment is configured for it.

### Run with Docker Compose

```bash
cd docker
docker-compose up --build
```

This starts:

- GlassFish app container on `http://localhost:8080`
- H2 database on ports `9092` and `8082`

The Docker setup expects the WAR file to already be built at:

```text
target/movie-actor-api-1.0-SNAPSHOT.war
```

## Example API Calls

```bash
curl http://localhost:8080/api/movies/list?page=1&size=10
curl http://localhost:8080/api/actors
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title":"The Matrix","genre":"Sci-Fi","movieYear":1999,"description":"A hacker learns reality is a simulation."}'
```

## Notes

- The project appears to be a learning/demo application rather than a production-ready API.
- The Docker configuration and POM indicate a WAR deployment flow rather than a standalone Spring Boot app.
- H2 is included for local testing, but a production-ready deployment would typically use a managed database and externalized configuration.

## License

No explicit license file was found in the repository. Check the repository settings or project documentation for licensing details before redistributing or using the code in production.
