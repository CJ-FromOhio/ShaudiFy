FROM gradle:8.12.1-jdk17 AS build
COPY build.gradle settings.gradle /build/
WORKDIR /build/
RUN gradle dependencies
COPY src /build/src
RUN gradle build -x test

# Run stage
FROM openjdk:17-alpine
ARG JAR_FILE=/build/build/libs/*.jar
COPY --from=build $JAR_FILE /opt/docker-test/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/docker-test/app.jar"]