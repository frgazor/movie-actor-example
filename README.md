# Movie Actor API

A Java-based REST API for managing movies and actors, built with JAX-RS, Hibernate, and a GlassFish deployment model. The project demonstrates a simple CRUD-style service with relationships between movies and actors, pagination metadata, caching, and Docker-based local deployment.

## Overview

This application exposes a JSON API under `/api` for creating and querying movie and actor records. It is designed as a lightweight example project for working with Java EE/JAX-RS services, ORM persistence, and servlet deployment.

## Features

- CRUD operations for movies and actors
- Relationship management between movies and actors
- Pagination support with response headers
- ETag-based caching for read operations
- Actor image retrieval
- H2 local database support
- Docker Compose setup for quick local deployment

## Tech Stack

- Java 8
- Maven
- JAX-RS / Jersey
- Hibernate ORM
- H2 Database
- GlassFish 5
- Docker / Docker Compose

## Project Structure

```text
.
├── database/
│   ├── db.txt
│   └── moviedb/
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── src/
│   ├── main/
│   │   ├── resources/
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── web.xml
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
├── pom.xml
├── .gitignore
├── README.md
└── LICENSE (if present in your repo)
```

## API Endpoints

### Movies

Base path: `/api/movies`

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/movies/list?page=1&size=10` | List movies with pagination metadata |
| GET | `/api/movies/{id}` | Fetch a specific movie |
| POST | `/api/movies` | Create a movie |
| PUT | `/api/movies/{id}` | Update a movie |
| DELETE | `/api/movies/{id}` | Delete a movie |
| POST | `/api/movies/{movieId}/actors/{actorId}` | Add an actor to a movie |
| DELETE | `/api/movies/{movieId}/actors/{actorId}` | Remove an actor from a movie |
| POST | `/api/movies/insertTestData` | Insert sample movie data |

### Actors

Base path: `/api/actors`

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/actors?page=1&size=10` | List actors with pagination metadata |
| GET | `/api/actors/{id}` | Fetch a specific actor |
| POST | `/api/actors` | Create an actor |
| PUT | `/api/actors/{id}` | Update an actor |
| DELETE | `/api/actors/{id}` | Delete an actor |
| GET | `/api/actors/{id}/picture` | Fetch an actor picture |
| POST | `/api/actors/insertTestData` | Insert sample actor data |

## Data Model

The application uses the following core entities:

- `Movie`
  - `movieId`
  - `title`
  - `genre`
  - `movieYear`
  - `description`
  - assigned actors

- `Actor`
  - `actorId`
  - `firstName`
  - `lastName`
  - `dateOfBirth`
  - `picture`

Database definitions for `movie`, `actor`, and `movie_actor` are stored in `database/db.txt`.

## Getting Started

### Prerequisites

- Java 8+
- Maven
- Docker and Docker Compose (optional for containerized run)

### Build the Project

```bash
mvn clean package
```

This generates a WAR artifact suitable for deployment to a servlet container.

### Run with Docker Compose

```bash
cd docker
docker-compose up --build
```

This starts the GlassFish application container and an H2 database container.

### Local Deployment

After building the project, deploy the WAR file to a Java EE servlet container such as GlassFish or Tomcat.

```bash
target/movie-actor-api-1.0-SNAPSHOT.war
```

## Example Requests

```bash
curl http://localhost:8080/api/movies/list?page=1&size=10
curl http://localhost:8080/api/actors

curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title":"The Matrix","genre":"Sci-Fi","movieYear":1999,"description":"A hacker learns reality is a simulation."}'
```

## Notes

This repository is a sample/demo application rather than a production-grade system. It is useful for understanding how a Java JAX-RS service integrates with JPA/Hibernate, binary data handling, and a servlet-based deployment flow.

## License

This project does not currently include a visible license file in the repository. If you plan to distribute or reuse this code, confirm the repository’s licensing terms before doing so.
