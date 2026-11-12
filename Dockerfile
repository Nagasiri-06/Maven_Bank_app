# Stage 1: Dedicated Test Stage
FROM maven:3.9.9-eclipse-temurin-17 AS test
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean test

# Stage 2: Build Package (runs tests) & Create Final Runtime Image
FROM test AS build
RUN mvn package

FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]