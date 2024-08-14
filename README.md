# Wrtr
A simple social media/blogging platform written in Java using Spring Boot, Thymeleaf and Hibernate.
Uses PostgreSQL as a database.

## How to run
### Locally
1. Download the source code
2. Set environment variables for connecting to the database. Example .env file
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/db
   SPRING_DATASOURCE_USERNAME=username
   SPRING_DATASOURCE_PASSWORD=password
   ```
3. Run `mvn package`
4. Run `java -jar {name of the jar archive}`

### Docker with manual compilation
1. Download the source
2. Set the environment variables for the database.  Example .env-docker file
   ```
   DB_URL=jdbc:postgresql://db:5432/wrtr
   DB_NAME=wrtr
   DB_USERNAME=postgres
   DB_PASSWORD=password
   ```
3. Run `mvn package`
4. Run `docker compose up --build` (--build flag tells Docker to rebuild all the containers)

### Docker by pulling the container from GitHub
It is also possible to get the app using the `ghcr.io` package made with GitHub actions
