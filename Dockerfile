FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/docanalyzer-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT export DB_PASSWORD=$(cat /run/secrets/db_password) && java -jar app.jar