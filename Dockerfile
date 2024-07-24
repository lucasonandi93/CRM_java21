# Usar la imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR a la imagen
COPY target/crm-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8383

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
