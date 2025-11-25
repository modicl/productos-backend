package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando los datos de un comentario no cumplen con las validaciones.
 * 
 * Resulta en una respuesta HTTP 400 (Bad Request)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 */
public class ComentarioNotValidException extends RuntimeException {
    
    public ComentarioNotValidException(String mensaje) {
        super(mensaje);
    }
}
