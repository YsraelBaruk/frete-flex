# Etapa 1: build da aplicacao
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q clean package -DskipTests

# Etapa 2: imagem de execucao
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN useradd --system --create-home --home-dir /app app \
    && chown -R app:app /app

COPY --from=build --chown=app:app /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
