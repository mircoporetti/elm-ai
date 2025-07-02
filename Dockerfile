# Use slim JRE image for running only
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the pre-built JAR from outside (the build job)
COPY elm-ai.jar elm-ai.jar

ENTRYPOINT ["java", "-jar", "elm-ai.jar"]