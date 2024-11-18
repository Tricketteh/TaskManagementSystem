FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/task-management-system.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
