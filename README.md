# Productos - Backend

API REST completa para la gestión de productos, categorías y países de origen del sistema HuertoHogar.

## 📋 Tabla de Contenidos
- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Docker](#docker)
- [Despliegue en Producción](#despliegue-en-producción)
- [Documentación API (Swagger)](#documentación-api-swagger)
- [Endpoints](#endpoints)
- [Autenticación JWT](#autenticación-jwt)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitectura](#arquitectura)

## ✨ Características

- ✅ **CRUD Completo** de Productos, Categorías y Países de Origen
- 🔐 **Autenticación JWT** con control de roles (ADMIN)
- 🔓 **Endpoints GET públicos** (sin autenticación requerida)
- 🔒 **Endpoints POST/PUT/PATCH/DELETE protegidos** (solo ADMIN)
- 📊 **Validaciones** de datos en tiempo real
- 🛡️ **Manejo centralizado de errores** con `@ControllerAdvice`
- 📚 **Documentación completa con Swagger/OpenAPI 3**
- 🔍 **Búsquedas especializadas** (por categoría, rango de precio)
- 💾 **Persistencia con JPA/Hibernate** en PostgreSQL

## 🔧 Requisitos

- **Java** 17+ (recomendado JDK 21)
- **Maven** 3.6+
- **PostgreSQL** 12+ (local o remoto)
- **Git** (para clonar el repositorio)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/modicl/productos-backend.git
cd productos-backend
```

### 2. Configurar variables de entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
DB_URL=jdbc:postgresql://HOST:PORT/DBNAME?sslmode=require
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
```

**Ejemplo con DigitalOcean:**
```env
DB_URL=jdbc:postgresql://db-postgresql-huertohogar-do-user-12345.db.ondigitalocean.com:25060/productos?sslmode=require
DB_USERNAME=doadmin
DB_PASSWORD=AVNS_xxxxxxxxxxxxx
```

### 3. Instalar dependencias

```bash
./mvnw clean install
```

### 4. Ejecutar la aplicación

**Windows PowerShell:**
```powershell
$env:DB_URL="jdbc:postgresql://HOST:PORT/DBNAME?sslmode=require"
$env:DB_USERNAME="usuario"
$env:DB_PASSWORD="password"
./mvnw spring-boot:run
```

**Linux/Mac:**
```bash
export DB_URL="jdbc:postgresql://HOST:PORT/DBNAME?sslmode=require"
export DB_USERNAME="usuario"
export DB_PASSWORD="password"
./mvnw spring-boot:run
```

## � Docker

### Build de la imagen

```bash
docker build -t productos-backend:latest .
```

### Run del contenedor

```bash
docker run -d \
  --name productos-backend \
  -p 8080:8080 \
  -e DB_URL="jdbc:postgresql://HOST:PORT/DBNAME?sslmode=require" \
  -e DB_USERNAME="usuario" \
  -e DB_PASSWORD="password" \
  productos-backend:latest
```

### Script Helper (Windows)

Para Windows PowerShell, usa el script helper incluido:

```powershell
./docker-helper.ps1
```

Este script te permite:
- 📦 Build de imagen Docker
- 🚀 Run del contenedor con configuración interactiva
- 📋 Ver logs en tiempo real
- 🏥 Verificar health del contenedor
- 🛑 Stop y remove del contenedor

## 🚀 Despliegue en Producción

### DigitalOcean App Platform

El proyecto incluye configuración lista para desplegar en **DigitalOcean App Platform**.

**Archivos de configuración:**
- `Dockerfile` - Multi-stage build optimizado
- `.dockerignore` - Optimización de build
- `.do/app.yaml` - Configuración de App Platform

**Guía completa de despliegue:** Ver [DEPLOYMENT.md](DEPLOYMENT.md)

**Pasos rápidos:**

1. Push del código a GitHub
2. Conectar repositorio en App Platform
3. Configurar variables de entorno (DB_URL, JWT_SECRET, etc.)
4. Deploy automático

**Costo estimado:** ~$20/mes (App + Database)

## �📚 Documentación API (Swagger)

Una vez iniciado el servidor, accede a la documentación interactiva:

- **Swagger UI (Interfaz visual):** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON (Especificación):** http://localhost:8080/v3/api-docs

### Características de la documentación:

✅ **Ejemplos completos** de peticiones y respuestas  
✅ **Probador integrado** (Try it out)  
✅ **Esquemas de datos** detallados  
✅ **Códigos de respuesta** documentados  
✅ **Autenticación JWT** integrada  

### Cómo usar Swagger UI:

1. Abre http://localhost:8080/swagger-ui.html
2. Explora los endpoints organizados por entidad (Productos, Categorías, Países)
3. Para endpoints protegidos:
   - Obtén un token JWT del microservicio de usuarios
   - Haz clic en el botón **"Authorize"** 🔓
   - Pega el token (sin "Bearer")
   - Prueba los endpoints protegidos

## Endpoints principales (base `/api/v1`)

### 🔓 Endpoints Públicos (sin autenticación)
- **GET** `/api/v1/productos` - Listar todos los productos
- **GET** `/api/v1/productos/{id}` - Obtener producto por ID
- **GET** `/api/v1/productos/categoria/{id}` - Productos por categoría
- **GET** `/api/v1/productos/precio?min=X&max=Y` - Productos por rango de precio
- **GET** `/api/v1/categorias` - Listar todas las categorías
- **GET** `/api/v1/categorias/{id}` - Obtener categoría por ID
- **GET** `/api/v1/paises` - Listar todos los países
- **GET** `/api/v1/paises/{id}` - Obtener país por ID

### 🔒 Endpoints Protegidos (requieren autenticación con rol ADMIN)
- **POST** `/api/v1/productos` - Crear producto
- **PUT** `/api/v1/productos/{id}` - Actualizar producto completo
- **PATCH** `/api/v1/productos/{id}` - Actualizar producto parcialmente
- **DELETE** `/api/v1/productos/{id}` - Eliminar producto
- **POST** `/api/v1/categorias` - Crear categoría
- **PUT** `/api/v1/categorias/{id}` - Actualizar categoría
- **PATCH** `/api/v1/categorias/{id}` - Actualizar categoría parcialmente
- **DELETE** `/api/v1/categorias/{id}` - Eliminar categoría
- **POST** `/api/v1/paises` - Crear país
- **PUT** `/api/v1/paises/{id}` - Actualizar país
- **PATCH** `/api/v1/paises/{id}` - Actualizar país parcialmente
- **DELETE** `/api/v1/paises/{id}` - Eliminar país

## 🔐 Autenticación JWT

Los endpoints protegidos requieren un token JWT válido con rol **ADMIN**.

### Cómo autenticarse en Swagger UI

1. Inicia sesión en el microservicio de **Usuarios** para obtener un token JWT
2. Copia el token generado
3. En Swagger UI, haz clic en el botón **"Authorize"** (candado verde en la parte superior)
4. Pega el token en el campo (sin agregar "Bearer ", solo el token)
5. Haz clic en **"Authorize"** y luego en **"Close"**
6. Ahora puedes ejecutar los endpoints protegidos

### Autenticación con Postman/cURL

Agrega el header `Authorization` con el valor:
```
Bearer <tu-token-jwt>
```

Ejemplo con cURL:
```bash
curl -X POST http://localhost:8080/api/v1/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -d '{"nombreProducto":"Nuevo Producto",...}'
```

### Respuestas de autenticación

- **401 Unauthorized**: Token no proporcionado o inválido
- **403 Forbidden**: Token válido pero sin permisos (requiere rol ADMIN)

## Ejemplo rápido (POST Producto desde Postman)

**Crear un producto (requiere autenticación ADMIN):**

Body JSON (crear):

```json
{
  "nombreProducto": "Tomate Cherry Orgánico",
  "categoria": { "idCategoria": 1 },
  "descripcionProducto": "Tomates cherry frescos cultivados sin pesticidas",
  "precioProducto": 2500,
  "stockProducto": 150,
  "paisOrigen": { "idPais": 1 },
  "imagenUrl": "https://ejemplo.com/imagenes/tomate-cherry.jpg"
}
```

**Headers requeridos:**
```
Content-Type: application/json
Authorization: Bearer <tu-token-jwt>
```

**Ejemplo con cURL:**
```bash
curl -X POST http://localhost:8080/api/v1/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGc..." \
  -d '{
    "nombreProducto": "Tomate Cherry Orgánico",
    "categoria": {"idCategoria": 1},
    "descripcionProducto": "Tomates cherry frescos",
    "precioProducto": 2500,
    "stockProducto": 150,
    "paisOrigen": {"idPais": 1},
    "imagenUrl": "https://ejemplo.com/tomate.jpg"
  }'
```

## Stack / herramientas

### Backend
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3.3.5
  - Spring Web (REST APIs)
  - Spring Data JPA (Persistencia)
  - Spring Boot DevTools (Desarrollo)

### Base de Datos
- **PostgreSQL** 18.0
- **Hibernate** 6.6.33 (ORM)
- **HikariCP** (Connection Pool)

### Seguridad
- **JJWT** 0.12.6 (JSON Web Tokens)
- Autenticación Bearer Token
- Control de acceso basado en roles

### Documentación
- **Swagger/OpenAPI 3** (springdoc-openapi 2.6.0)
- Interfaz interactiva Swagger UI
- Ejemplos de peticiones/respuestas

### Herramientas de Desarrollo
- **Maven** (Gestión de dependencias)
- **Lombok** (Reducción de boilerplate)
- **spring-dotenv** 4.0.0 (Variables de entorno)

### Calidad de Código
- JavaDoc completo en clases principales
- Manejo centralizado de excepciones
- Validaciones en capa de servicio

## 🏗️ Arquitectura

### Patrón de Capas

```
┌─────────────────────────────────────┐
│     Controller Layer (REST)         │  ← Endpoints HTTP
│  - ProductoController               │
│  - CategoriaController              │
│  - PaisOrigenController             │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Service Layer (Lógica)         │  ← Validaciones
│  - ProductoService                  │
│  - CategoriaService                 │
│  - PaisOrigenService                │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Repository Layer (Persistencia)   │  ← JPA/Hibernate
│  - ProductoRepository               │
│  - CategoriaRepository              │
│  - PaisOrigenRepository             │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         PostgreSQL Database         │
└─────────────────────────────────────┘
```

### Componentes Transversales

```
┌─────────────────────────────────────┐
│  RoleCheckInterceptor (JWT)         │  ← Seguridad
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  GlobalExceptionHandler             │  ← Manejo de errores
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  OpenAPIConfig (Swagger)            │  ← Documentación
└─────────────────────────────────────┘
```

### Modelo de Datos

```
┌─────────────────┐
│    Producto     │
├─────────────────┤
│ id_producto  PK │
│ nombre          │
│ descripcion     │
│ precio          │
│ stock           │
│ imagen_url      │
│ id_categoria FK │──┐
│ id_pais_orig FK │  │
└─────────────────┘  │
                     │
       ┌─────────────┴──────────────┐
       │                            │
┌──────▼──────┐            ┌────────▼──────┐
│  Categoria  │            │  PaisOrigen   │
├─────────────┤            ├───────────────┤
│ id_cat   PK │            │ id_pais    PK │
│ nombre      │            │ nombre        │
│ descripcion │            └───────────────┘
└─────────────┘
```

## 📝 Validaciones Implementadas

### Producto
- ✅ Nombre: No vacío
- ✅ Precio: > 0
- ✅ Stock: >= 0
- ✅ Categoría: Debe existir
- ✅ País: Debe existir

### Categoría
- ✅ Nombre: No vacío
- ✅ Descripción: No vacía

### País de Origen
- ✅ Nombre: No vacío

## 🔒 Seguridad y Permisos

| Método HTTP | Endpoint | Autenticación | Rol Requerido |
|------------|----------|---------------|---------------|
| GET | Todos | ❌ No | Público |
| POST | Todos | ✅ Sí | ADMIN |
| PUT | Todos | ✅ Sí | ADMIN |
| PATCH | Todos | ✅ Sí | ADMIN |
| DELETE | Todos | ✅ Sí | ADMIN |

## 🐛 Manejo de Errores

Todas las respuestas de error siguen este formato JSON:

```json
{
  "timestamp": "2025-11-11T14:30:00",
  "message": "Descripción del error",
  "status": 400
}
```

### Códigos de Estado HTTP

| Código | Descripción | Cuándo ocurre |
|--------|-------------|---------------|
| 200 | OK | Operación exitosa |
| 201 | Created | Recurso creado |
| 204 | No Content | Recurso eliminado |
| 400 | Bad Request | Datos inválidos |
| 401 | Unauthorized | Token inválido/ausente |
| 403 | Forbidden | Sin permisos (no ADMIN) |
| 404 | Not Found | Recurso no existe |
| 500 | Internal Server Error | Error del servidor |

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
