# Usa Java 17
FROM eclipse-temurin:17-jdk-jammy

# Crea el directorio de la app dentro del contenedor
WORKDIR /app

# Copia cualquier JAR dentro de target/ al contenedor y lo renombra a app.jar
COPY target/*.jar app.jar

# Expone el puerto que use tu Spring Boot (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar el JAR
ENTRYPOINT ["java","-jar","app.jar"]

