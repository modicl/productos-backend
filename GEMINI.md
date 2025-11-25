# Productos - Backend Microservice

This is a Spring Boot-based backend microservice for the **HuertoHogar** system, responsible for managing the product catalog, categories, and countries of origin. It exposes a RESTful API documented with OpenAPI/Swagger.

## 📋 Project Overview

*   **Type:** Java / Spring Boot Application
*   **Purpose:** REST API for Product Management (CRUD)
*   **Language:** Java 21
*   **Framework:** Spring Boot 3.4.0
*   **Build System:** Maven
*   **Database:** PostgreSQL
*   **Authentication:** JWT (JSON Web Token)
*   **Documentation:** Swagger / OpenAPI 3

## 🏗️ Architecture

The project follows a standard layered architecture:

1.  **Controller Layer (`cl.huertohogar.productos.controller`):** Handles HTTP requests and maps them to service methods.
2.  **Service Layer (`cl.huertohogar.productos.service`):** Contains business logic and validations.
3.  **Repository Layer (`cl.huertohogar.productos.repository`):** Manages data persistence using Spring Data JPA and Hibernate.
4.  **Model Layer (`cl.huertohogar.productos.model`):** JPA Entities representing database tables (`Producto`, `Categoria`, `PaisOrigen`, `Comentario`).
5.  **DTO Layer (`cl.huertohogar.productos.dto`):** Data Transfer Objects for API requests and responses.
6.  **Exception Handling (`cl.huertohogar.productos.exception`):** Centralized error handling via `@ControllerAdvice`.

## 🛠️ Key Technologies & Libraries

*   **Spring Boot Web:** For building RESTful services.
*   **Spring Data JPA:** For database interaction.
*   **Lombok:** For reducing boilerplate code (Getters, Setters, Constructors).
*   **SpringDoc OpenAPI:** For automatic Swagger documentation (`/swagger-ui.html`).
*   **JJWT:** For handling JSON Web Tokens.
*   **Spring Cloud OpenFeign:** For declarative REST client (communicating with User microservice).
*   **Spring Dotenv:** For loading environment variables from `.env` files.

## 🚀 Building and Running

### Prerequisites

*   Java 21
*   Maven (Wrapper included)
*   PostgreSQL instance

### Environment Variables

The application requires the following environment variables (can be set in `.env` or system env):

```env
DB_URL=jdbc:postgresql://HOST:PORT/DBNAME?sslmode=require
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
```

### Build Commands

*   **Clean and Install:**
    ```bash
    ./mvnw clean install
    ```
*   **Compile:**
    ```bash
    ./mvnw compile
    ```

### Run Commands

*   **Run Locally:**
    ```bash
    ./mvnw spring-boot:run
    ```
    *Note: Ensure env vars are set before running.*

*   **Run with Docker:**
    ```bash
    docker build -t productos-backend .
    docker run -p 8080:8080 -e DB_URL=... -e DB_USERNAME=... -e DB_PASSWORD=... productos-backend
    ```

## 🔌 API Endpoints

The base URL for the API is `/api/v1`.

### Public Endpoints (No Auth)
*   `GET /api/v1/productos` - List all products
*   `GET /api/v1/productos/{id}` - Get product by ID
*   `GET /api/v1/productos/categoria/{id}` - Get products by category
*   `GET /api/v1/productos/precio?min=X&max=Y` - Get products by price range
*   `GET /api/v1/categorias` - List all categories
*   `GET /api/v1/paises` - List all countries
*   `GET /api/v1/comentarios/{idProducto}` - Get comments for a product

### Protected Endpoints (Requires ADMIN Role)
*   `POST /api/v1/productos` - Create product
*   `PUT /api/v1/productos/{id}` - Update product
*   `DELETE /api/v1/productos/{id}` - Delete product
*   *Similar CRUD endpoints exist for Categories and Countries.*

### Authentication
Protected endpoints require a **Bearer Token** in the `Authorization` header. The token must be signed with the secret configured in `application.properties`.

## 📂 Project Structure

```
src/main/java/cl/huertohogar/productos/
├── ProductosApplication.java  # Main entry point
├── client/                    # Feign clients (e.g., UsuarioFeignClient)
├── config/                    # Configuration (CORS, OpenAPI, Security)
├── controller/                # REST Controllers
├── dto/                       # Data Transfer Objects
├── exception/                 # Custom exceptions & Global handler
├── model/                     # JPA Entities
├── repository/                # JPA Repositories
├── service/                   # Business Logic
└── util/                      # Utilities (JwtUtil)
```

## 📏 Development Conventions

*   **Code Style:** Standard Java conventions. Use Lombok annotations (`@Data`, `@AllArgsConstructor`, etc.) to keep classes clean.
*   **API Response:** All errors return a standardized JSON structure: `{ timestamp, message, status }`.
*   **Validation:** Validate inputs in the Service layer or using Bean Validation annotations (`@NotNull`, `@Min`, etc.) in DTOs/Entities.
*   **Documentation:** Always annotate Controllers and DTOs with Swagger annotations (`@Operation`, `@Schema`, `@Tag`) to keep the API documentation up-to-date.
*   **Git:** Follow conventional commits (e.g., `feat: ...`, `fix: ...`).

## 📄 Key Files
*   `README.md`: General project documentation.
*   `API_ENDPOINTS.md`: Detailed guide for frontend consumers.
*   `application.properties`: Main configuration file.
*   `pom.xml`: Maven dependencies.
*   `Dockerfile`: Docker build configuration.
