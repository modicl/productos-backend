package cl.huertohogar.productos.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Productos - Huerto Hogar",
        description = "API REST para gestión de productos, categorías y países de origen",
        version = "1.0.0",
        contact = @Contact(
            name = "Huerto Hogar",
            email = "contacto@huertohogar.cl"
        )
    ),
    servers = {
        @Server(
            description = "Local",
            url = "http://localhost:8080"
        )
    }
)
public class OpenAPIConfig {
}
