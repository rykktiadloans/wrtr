# Wrtr
A simple social media/blogging platform written in Java using Spring Boot, Thymeleaf and Hibernate.
Uses PostgreSQL as a database.

### How to run
1. Download the source code
2. Set environment variables for connecting to the database. Example .env file
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/db
   SPRING_DATASOURCE_USERNAME=username
   SPRING_DATASOURCE_PASSWORD=password
   ```
3. Run `mvn package`
4. Run `java -jar {name of the jar archive}`