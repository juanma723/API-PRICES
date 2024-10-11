FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/testinditex-0.0.1-SNAPSHOT.jar /app/testinditex-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/testinditex-0.0.1-SNAPSHOT.jar"]
