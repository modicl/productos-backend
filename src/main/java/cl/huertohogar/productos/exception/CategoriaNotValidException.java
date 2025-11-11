package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando los datos de una categoría no cumplen con las validaciones.
 * 
 * Esta excepción se utiliza cuando:
 * - El nombre de la categoría está vacío
 * - La descripción está vacía
 * - Campos obligatorios están ausentes
 * 
 * Resulta en una respuesta HTTP 400 (Bad Request)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 * @see cl.huertohogar.productos.service.CategoriaService
 */
public class CategoriaNotValidException extends RuntimeException{
    
    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     * @param mensaje Descripción específica de la validación fallida
     */
    public CategoriaNotValidException(String mensaje){
        super(mensaje);
    }   
}
