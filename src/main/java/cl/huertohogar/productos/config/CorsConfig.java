package cl.huertohogar.productos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para permitir
 * que aplicaciones frontend alojadas en dominios diferentes puedan
 * consumir la API.
 * 
 * Esta configuración permite:
 * - Frontend en DigitalOcean Spaces
 * - Desarrollo local (localhost)
 * - Métodos HTTP necesarios para CRUD
 * - Headers de autenticación JWT
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // Orígenes permitidos
                .allowedOrigins(
                    "https://huertohogar.nyc3.cdn.digitaloceanspaces.com",  // Producción
                    "http://localhost:3000",                                 // React dev
                    "http://localhost:5173",                                 // Vite dev
                    "http://localhost:4200"                                  // Angular dev (si lo usas)
                )
                // Métodos HTTP permitidos
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                // Headers permitidos (incluyendo Authorization para JWT)
                .allowedHeaders("*")
                // Permitir envío de credenciales (cookies, authorization headers)
                .allowCredentials(true)
                // Headers que el frontend puede leer en la respuesta
                .exposedHeaders("Authorization", "Content-Type")
                // Tiempo de cache para preflight requests (1 hora)
                .maxAge(3600);
    }

    /**
     * Configuración alternativa usando CorsConfigurationSource
     * Útil si necesitas más control o múltiples configuraciones
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos
        configuration.setAllowedOrigins(Arrays.asList(
            "https://huertohogar.nyc3.cdn.digitaloceanspaces.com",
            "http://localhost:3000",
            "http://localhost:5173",
            "http://localhost:4200"
        ));
        
        // Métodos permitidos
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir credenciales
        configuration.setAllowCredentials(true);
        
        // Headers expuestos
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Aplicar configuración a todas las rutas /api/**
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        
        return source;
    }
}
