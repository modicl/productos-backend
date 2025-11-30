#  HuertoHogar - Microservicio de Productos

API REST para la gestión de productos del marketplace HuertoHogar.

##  Requisitos Previos

- Java 21+
- Maven 3.8+
- PostgreSQL 14+

##  Instalación Rápida

```bash
# 1. Clonar repositorio
git clone https://github.com/modicl/productos-backend.git
cd productos-backend

# 2. Crear archivo .env en la raíz
```

### Variables de Entorno (.env)

```env
DB_URL=jdbc:postgresql://localhost:5432/huertohogar_productos
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_password
```

```bash
# 3. Compilar
mvn clean install

# 4. Ejecutar
mvn spring-boot:run
```

##  URLs Importantes

| Recurso | URL |
|---------|-----|
| API Base | `http://localhost:8080/api/v1` |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| API Docs | `http://localhost:8080/v3/api-docs` |
| Health Check | `http://localhost:8080/actuator/health` |

---

##  Endpoints

###  Productos (`/api/v1/productos`)

| Método | Endpoint | Descripción | Auth | Rol |
|--------|----------|-------------|------|-----|
| `GET` | `/` | Listar todos los productos |  | - |
| `GET` | `/{id}` | Obtener producto por ID |  | - |
| `POST` | `/` | Crear producto |  | ADMIN |
| `PUT` | `/{id}` | Actualizar producto completo |  | ADMIN |
| `PATCH` | `/{id}` | Actualizar producto parcial |  | ADMIN |
| `DELETE` | `/{id}` | Eliminar producto |  | ADMIN |
| `GET` | `/categoria/{id}` | Productos por categoría |  | - |
| `GET` | `/precio?min=X&max=Y` | Productos por rango precio |  | - |
| `POST` | `/stock/actualizar` | Actualizar stock (interno) |  | API-Key |

#### Ejemplo POST Producto:
```json
{
  "nombreProducto": "Tomate Cherry Orgánico",
  "categoria": { "idCategoria": 1 },
  "descripcionProducto": "Tomates frescos y orgánicos",
  "precioProducto": 2500,
  "stockProducto": 150,
  "stockCritico": 20,
  "paisOrigen": { "idPais": 1 },
  "imagenUrl": "https://ejemplo.com/tomate.jpg"
}
```

---

###  Categorías (`/api/v1/categorias`)

| Método | Endpoint | Descripción | Auth | Rol |
|--------|----------|-------------|------|-----|
| `GET` | `/` | Listar categorías |  | - |
| `GET` | `/{id}` | Obtener categoría por ID |  | - |
| `POST` | `/` | Crear categoría |  | ADMIN |
| `PUT` | `/{id}` | Actualizar categoría |  | ADMIN |
| `PATCH` | `/{id}` | Actualizar parcial |  | ADMIN |
| `DELETE` | `/{id}` | Eliminar categoría |  | ADMIN |

#### Ejemplo POST Categoría:
```json
{
  "nombreCategoria": "Verduras",
  "descripcionCategoria": "Verduras frescas y orgánicas"
}
```

---

###  Países de Origen (`/api/v1/paises`)

| Método | Endpoint | Descripción | Auth | Rol |
|--------|----------|-------------|------|-----|
| `GET` | `/` | Listar países |  | - |
| `GET` | `/{id}` | Obtener país por ID |  | - |
| `POST` | `/` | Crear país |  | ADMIN |
| `PUT` | `/{id}` | Actualizar país |  | ADMIN |
| `PATCH` | `/{id}` | Actualizar parcial |  | ADMIN |
| `DELETE` | `/{id}` | Eliminar país |  | ADMIN |

#### Ejemplo POST País:
```json
{
  "nombre": "Chile"
}
```

---

###  Comentarios (`/api/v1/comentarios`)

| Método | Endpoint | Descripción | Auth | Rol |
|--------|----------|-------------|------|-----|
| `GET` | `/{idProducto}` | Obtener comentarios por producto |  | - |
| `POST` | `/` | Crear comentario |  | - |

#### Ejemplo POST Comentario:
```json
{
  "idProducto": 1,
  "usuarioId": 5,
  "comentario": "Excelente producto, muy buena calidad",
  "calificacion": 5,
  "fecha": "2025-11-24"
}
```

---

###  Actualización de Stock (Interno - Microservicios)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/stock/actualizar` | Descontar stock |
| `POST` | `/stock/revertir` | Revertir stock |

#### Ejemplo Body:
```json
{
  "idOrden": 12345,
  "items": [
    { "idProducto": 4, "cantidad": 2 },
    { "idProducto": 6, "cantidad": 1 }
  ]
}
```

---

##  Autenticación

Los endpoints protegidos requieren token JWT en el header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Roles:
- `USER`: Usuario normal
- `ADMIN`: Administrador (CRUD completo)

---

##  Docker

```bash
# Construir imagen
docker build -t productos-backend .

# Ejecutar
docker run -p 8080:8080 --env-file .env productos-backend
```

---

##  Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| `200` | Éxito |
| `201` | Creado exitosamente |
| `204` | Eliminado exitosamente |
| `400` | Datos inválidos |
| `401` | No autorizado |
| `403` | Acceso denegado |
| `404` | No encontrado |
| `409` | Conflicto (stock insuficiente) |
| `500` | Error interno |

---

##  Tecnologías

- **Framework**: Spring Boot 3.4.0
- **Java**: 21
- **Base de Datos**: PostgreSQL
- **ORM**: Hibernate/JPA
- **Documentación**: SpringDoc OpenAPI (Swagger)
- **Seguridad**: JWT
- **Build**: Maven

---

##  Autores

- HuertoHogar Team - Duoc UC

---

##  Licencia

Este proyecto es parte del curso Fullstack - Duoc UC 2025.
