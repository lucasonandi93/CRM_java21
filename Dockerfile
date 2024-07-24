# Usar la imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Copiar el archivo JAR a la imagen
COPY target/crm-0.0.1-SNAPSHOT.jar app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
