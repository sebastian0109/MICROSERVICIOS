# Etapa 1: compilar
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
# Copiar solo el pom primero permite cachear las dependencias entre builds
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
# Las pruebas se ejecutan en el pipeline CI, no aquí
RUN mvn -q clean package -DskipTests

# Etapa 2: imagen final
FROM eclipse-temurin:17-jre-alpine
# Usuario sin privilegios (buena práctica de seguridad)
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]