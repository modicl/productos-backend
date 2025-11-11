package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando los datos de un país de origen no cumplen con las validaciones.
 * 
 * Esta excepción se utiliza cuando:
 * - El nombre del país está vacío
 * - Campos obligatorios están ausentes
 * - Datos inválidos o formato incorrecto
 * 
 * Resulta en una respuesta HTTP 400 (Bad Request)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 * @see cl.huertohogar.productos.service.PaisOrigenService
 */
public class PaisOrigenNotValidException extends RuntimeException {
    
    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     * @param mensaje Descripción específica de la validación fallida
     */
    public PaisOrigenNotValidException(String mensaje){
        super(mensaje);
    }
}
