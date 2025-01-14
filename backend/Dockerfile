FROM maven:3.8.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY ./pom.xml .
COPY src ./src

RUN mvn -f /app/pom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
