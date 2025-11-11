package cl.huertohogar.productos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Productos - Huerto Hogar")
                .description("""
                    API REST para la gestión completa de productos, categorías y países de origen del sistema HuertoHogar.
                    
                    ## Características principales:
                    - ✅ CRUD completo de Productos, Categorías y Países de Origen
                    - 🔓 Endpoints GET públicos (sin autenticación)
                    - 🔒 Endpoints POST/PUT/PATCH/DELETE protegidos (requieren rol ADMIN)
                    - 🔐 Autenticación mediante JWT (JSON Web Tokens)
                    - 📊 Validaciones de datos en tiempo real
                    - 🛡️ Manejo centralizado de errores
                    
                    ## Autenticación:
                    1. Obtén un token JWT desde el microservicio de usuarios
                    2. Haz clic en el botón **"Authorize"** (🔓 candado arriba)
                    3. Ingresa el token JWT (sin agregar "Bearer")
                    4. Ahora puedes ejecutar endpoints protegidos
                    
                    ## Códigos de respuesta:
                    - **200**: Operación exitosa
                    - **201**: Recurso creado exitosamente
                    - **204**: Recurso eliminado exitosamente
                    - **400**: Datos inválidos o error de validación
                    - **401**: No autorizado - Token inválido o no proporcionado
                    - **403**: Acceso denegado - Requiere rol ADMIN
                    - **404**: Recurso no encontrado
                    - **500**: Error interno del servidor
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("Equipo HuertoHogar")
                    .email("contacto@huertohogar.cl")
                    .url("https://huertohogar.cl")))
            .addServersItem(new Server()
                .url("http://localhost:8080")
                .description("Servidor de Desarrollo Local"))
            .addServersItem(new Server()
                .url("https://api.huertohogar.cl")
                .description("Servidor de Producción"))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("""
                            Autenticación mediante JSON Web Token (JWT).
                            
                            **Cómo obtener el token:**
                            1. Autentícate en el microservicio de usuarios: POST /api/auth/login
                            2. Copia el token JWT de la respuesta
                            3. Pégalo aquí (sin agregar "Bearer " manualmente)
                            
                            **El token debe contener:**
                            - Usuario autenticado
                            - Rol: ADMIN (para endpoints protegidos)
                            - Tiempo de expiración: 24 horas
                            """)))
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }
}
