FROM openjdk:17-jdk-slim

# Setting default server port for spring app in container
ENV SERVER_PORT=8080
WORKDIR /app

ADD target/core-game-service-0.0.1-SNAPSHOT.jar /app

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "core-game-service-0.0.1-SNAPSHOT.jar"]
