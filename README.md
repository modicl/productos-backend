# Productos - Backend

Breve backend REST para gestión de productos, categorías y países de origen.

## Qué hace
- CRUD de Productos, Categorías y Países de Origen
- Validaciones y manejo centralizado de errores con `@ControllerAdvice`
- Persistencia con JPA/Hibernate en PostgreSQL

## Requisitos
- Java 17+ (se usa JDK 21 en el entorno de desarrollo)
- Maven
- PostgreSQL (o acceso a una instancia remota)

## Variables de entorno (usar `.env` o variables del sistema)
- DB_URL (ej. jdbc:postgresql://host:5432/dbname?sslmode=require)
- DB_USERNAME
- DB_PASSWORD

> Se recomienda no commitear `.env` ni credenciales en `application.properties`.

## Ejecutar (desarrollo, Windows PowerShell)
1. Definir variables de entorno (ejemplo):

```powershell
$env:DB_URL="jdbc:postgresql://HOST:PORT/DBNAME?sslmode=require"
$env:DB_USERNAME="usuario"
$env:DB_PASSWORD="password"
```

2. Ejecutar con Maven (desde la carpeta del proyecto):

```powershell
./mvnw spring-boot:run
# o con Java directamente si ya generaste el jar
./mvnw package
java -jar target/*.jar
```

3. Acceder a la documentación Swagger UI:

Una vez iniciado el servidor, abre en tu navegador:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## Endpoints principales (base `/api/v1`)
- Productos: `/productos`
  - GET `/api/v1/productos` - listar
  - GET `/api/v1/productos/{id}` - obtener
  - POST `/api/v1/productos` - crear
  - PUT `/api/v1/productos/{id}` - reemplazar
  - PATCH `/api/v1/productos/{id}` - actualizar parcialmente
  - DELETE `/api/v1/productos/{id}` - borrar
- Categorías: `/categorias` (CRUD similar)
- Países: `/paises` (CRUD similar)

## Ejemplo rápido (POST Producto desde Postman)
Body JSON (crear):

```json
{
  "nombreProducto": "Tomate Cherry Orgánico",
  "categoria": { "idCategoria": 1 },
  "descripcionProducto": "Tomates cherry frescos...",
  "precioProducto": 2500,
  "stockProducto": 150,
  "paisOrigen": { "idPais": 1 },
  "imagenUrl": "https://ejemplo.com/imagenes/tomate-cherry.jpg"
}
```

## Stack / herramientas
- Lenguajes: Java
- Frameworks/librerías: Spring Boot (Spring Web, Spring Data JPA), Hibernate
- Documentación API: Swagger/OpenAPI 3 (springdoc-openapi)
- Base de datos: PostgreSQL
- Build: Maven (wrapper `mvnw` incluido)
- Otras: Lombok, HikariCP, spring-dotenv

## Notas
- El proyecto usa validaciones en servicios y `GlobalExceptionHandler` para respuestas consistentes.
- Para forzar orden o esquema de tablas usa scripts SQL y configura `spring.jpa.hibernate.ddl-auto` a `validate` o `none`.

---
Archivo generado automáticamente por el asistente. Mantener actualizado según cambios en la API.
