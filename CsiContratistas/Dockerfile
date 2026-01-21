# Usa una imagen de Java 17 como base
FROM eclipse-temurin:17-jdk AS builder

# Crea el directorio de la app
WORKDIR /app

# Copia el JAR generado
COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto que usa Spring Boot (personalizable)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
