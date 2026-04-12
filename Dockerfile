# Base image: eclipse-temurin JDK 17 (Alpine — lightweight)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml first for dependency layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Grant execute permission for the Maven wrapper script
RUN chmod +x mvnw

# Pre-download dependencies (cached layer — speeds up rebuilds)
RUN ./mvnw dependency:go-offline -B

# Copy the full source code
COPY src ./src

# Build the application JAR (skip tests for faster image builds)
RUN ./mvnw package -DskipTests

# Expose app port
EXPOSE 8080

# Run the JAR — Spring profile set via docker-compose environment variable
CMD ["java", "-jar", "target/cms-0.0.1-SNAPSHOT.jar"]
