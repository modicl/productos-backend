package cl.huertohogar.productos.exception;

/**
 * Excepción lanzada cuando no se encuentra un país de origen en el sistema.
 * 
 * Esta excepción se utiliza típicamente cuando:
 * - Se busca un país por ID que no existe
 * - Se intenta actualizar o eliminar un país inexistente
 * - Se referencia un país que ha sido eliminado
 * 
 * Resulta en una respuesta HTTP 404 (Not Found)
 * 
 * @see cl.huertohogar.productos.exception.GlobalExceptionHandler
 */
public class PaisOrigenNotFoundException extends RuntimeException {
    
    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     * @param mensaje Descripción detallada del error
     */
    public PaisOrigenNotFoundException(String mensaje) {
        super(mensaje);
    }
}
    
