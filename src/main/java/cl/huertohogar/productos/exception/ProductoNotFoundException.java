package cl.huertohogar.productos.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String mensaje){
        super(mensaje);
    }
    
}
