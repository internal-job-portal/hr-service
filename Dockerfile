# Build stage
FROM docker.io/eclipse-temurin:21-jdk-jammy AS build
ENV HOME=/usr/app/hr-service
RUN mkdir -p $HOME
WORKDIR $HOME

# Copy gradle wrapper and source code
COPY build.gradle $HOME/
COPY settings.gradle $HOME/
COPY gradlew $HOME/
COPY gradle $HOME/gradle
COPY src $HOME/src

# Build the application with Gradle
RUN ./gradlew clean build -x test --no-daemon

# Package stage
FROM docker.io/eclipse-temurin:21-jre-jammy
COPY --from=build /usr/app/hr-service/build/libs/*.jar /app/hr-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/hr-service.jar"]