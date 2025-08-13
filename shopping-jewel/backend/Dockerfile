# ========================
# ========================
# 1️⃣ Build stage
# ========================
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
# Copy the whole project so root pom.xml is present
COPY . .
# Build only the backend module
RUN mvn -pl backend dependency:go-offline -B
RUN mvn -pl backend clean package -DskipTests

# ========================
# 2️⃣ Runtime stage
# ========================
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
