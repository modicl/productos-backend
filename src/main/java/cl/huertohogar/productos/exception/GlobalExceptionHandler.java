package cl.huertohogar.productos.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * Manejador global de excepciones para la API de Productos.
 * 
 * Proporciona un manejo centralizado y consistente de errores,
 * retornando respuestas JSON estructuradas con:
 * - timestamp: Fecha y hora del error
 * - message: Descripción del error
 * - status: Código HTTP del error
 * 
 * Códigos de error:
 * - 400 BAD_REQUEST: Datos inválidos o validación fallida
 * - 404 NOT_FOUND: Recurso no encontrado
 * - 500 INTERNAL_SERVER_ERROR: Error inesperado del servidor
 */
@ControllerAdvice
@Hidden // Oculta este controller del Swagger UI
public class GlobalExceptionHandler {
    
    // ==================== PRODUCTO ====================
    
    /**
     * Maneja errores cuando no se encuentra un producto.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 404 y detalles del error
     */
    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductoNotFoundException(ProductoNotFoundException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja errores de validación de datos de producto.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 400 y detalles del error
     */
    @ExceptionHandler(ProductoNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleProductoNotValidException(ProductoNotValidException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ==================== CATEGORIA ====================
    
    /**
     * Maneja errores de validación de datos de categoría.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 400 y detalles del error
     */
    @ExceptionHandler(CategoriaNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleCategoriaNotValidException(CategoriaNotValidException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores cuando no se encuentra una categoría.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 404 y detalles del error
     */
    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoriaNotFoundException(CategoriaNotFoundException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // ==================== PAIS ORIGEN ====================
    
    /**
     * Maneja errores cuando no se encuentra un país de origen.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 404 y detalles del error
     */
    @ExceptionHandler(PaisOrigenNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePaisOrigenNotFoundException(PaisOrigenNotFoundException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja errores de validación de datos de país de origen.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 400 y detalles del error
     */
    @ExceptionHandler(PaisOrigenNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlePaisOrigenNotValidException(PaisOrigenNotValidException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ==================== EXCEPCIONES GENERALES ====================
    
    /**
     * Maneja cualquier excepción no contemplada específicamente.
     * Catch-all para errores inesperados del servidor.
     * @param ex Excepción lanzada
     * @return ResponseEntity con código 500 y detalles del error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", "Error interno del servidor: " + ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
