# Usar la imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Crear un directorio para la aplicación
RUN mkdir /app

# Copiar el archivo JAR a la imagen
COPY target/crm-0.0.1-SNAPSHOT.jar /app/app.jar

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto
EXPOSE 8383

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]