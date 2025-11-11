# Dockerfile para Productos Backend - DigitalOcean App Platform
# Multi-stage build para optimizar el tamaño de la imagen

# ==================== STAGE 1: BUILD ====================
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar la aplicación (sin ejecutar tests para acelerar build)
RUN mvn clean package -DskipTests

# ==================== STAGE 2: RUNTIME ====================
FROM eclipse-temurin:21-jre-alpine

# Metadata
LABEL maintainer="HuertoHogar <contacto@huertohogar.cl>"
LABEL description="API REST de Productos - HuertoHogar"
LABEL version="1.0.0"

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar ownership del directorio
RUN chown -R appuser:appgroup /app

# Cambiar a usuario no-root
USER appuser

# Exponer el puerto 8080
EXPOSE 8080

# Variables de entorno por defecto (se sobrescriben en App Platform)
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SERVER_PORT=8080 \
    SPRING_PROFILES_ACTIVE=prod

# Healthcheck para verificar que la aplicación está corriendo
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
