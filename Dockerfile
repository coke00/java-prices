# Build stage
FROM openjdk:17-slim as build
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/prueba-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
