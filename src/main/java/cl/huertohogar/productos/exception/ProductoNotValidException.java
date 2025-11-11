package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando los datos de un producto no cumplen con las validaciones.
 * 
 * Esta excepción se utiliza cuando:
 * - El nombre del producto está vacío
 * - El precio es menor o igual a 0
 * - El stock es negativo
 * - Campos obligatorios están ausentes
 * 
 * Resulta en una respuesta HTTP 400 (Bad Request)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 * @see cl.huertohogar.productos.service.ProductoService
 */
public class ProductoNotValidException extends RuntimeException {
    
    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     * @param mensaje Descripción específica de la validación fallida
     */
    public ProductoNotValidException(String mensaje) {
        super(mensaje);
    }
}
