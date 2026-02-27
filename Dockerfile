# Use lightweight JDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy jar from target folder into container
COPY target/routing-graph-0.0.1-SNAPSHOT.jar app.jar

# Expose application port (change if different)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]