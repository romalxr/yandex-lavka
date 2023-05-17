# Stage 1: Build
FROM openjdk:17.0.1-jdk-slim AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradlew ./
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew bootJar

# Stage 2: Copy and run the built jar
FROM openjdk:17.0.1-jdk-slim
WORKDIR /opt/app
COPY --from=build /app/build/libs/*.jar yandex-lavka.jar
ENTRYPOINT ["java","-jar","yandex-lavka.jar"]
