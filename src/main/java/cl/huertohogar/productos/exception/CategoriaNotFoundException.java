package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando no se encuentra una categoría en el sistema.
 * 
 * Esta excepción se utiliza típicamente cuando:
 * - Se busca una categoría por ID que no existe
 * - Se intenta actualizar o eliminar una categoría inexistente
 * - Se referencia una categoría que ha sido eliminada
 * 
 * Resulta en una respuesta HTTP 404 (Not Found)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 */
public class CategoriaNotFoundException extends RuntimeException {
    
    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     * @param mensaje Descripción detallada del error
     */
    public CategoriaNotFoundException(String mensaje) {
        super(mensaje);
    }
}

