package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando no se encuentra un producto en el sistema.
 * 
 * Esta excepción se utiliza típicamente cuando:
 * - Se busca un producto por ID que no existe
 * - Se intenta actualizar o eliminar un producto inexistente
 * 
 * Resulta en una respuesta HTTP 404 (Not Found)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 */
public class ProductoNotFoundException extends RuntimeException {
    
    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     * @param mensaje Descripción detallada del error
     */
    public ProductoNotFoundException(String mensaje){
        super(mensaje);
    }
}
