package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando no se encuentra un comentario en el sistema.
 * 
 * Resulta en una respuesta HTTP 404 (Not Found)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 */
public class ComentarioNotFoundException extends RuntimeException {
    
    public ComentarioNotFoundException(String mensaje){
        super(mensaje);
    }
}
