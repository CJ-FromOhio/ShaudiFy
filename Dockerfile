FROM openjdk:17-jdk-slim
EXPOSE 8080
WORKDIR /app
COPY build/libs/shaudify-demo-1-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar","application.jar"]