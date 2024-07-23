# Utiliza una imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/crm-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8383

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
