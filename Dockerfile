# Dockerfile
FROM openjdk:8-jre-alpine
MAINTAINER Diego Leonel Cañete Crescini
WORKDIR /app
COPY ./target/accountingNotebook-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "accountingNotebook-0.0.1-SNAPSHOT.jar"]