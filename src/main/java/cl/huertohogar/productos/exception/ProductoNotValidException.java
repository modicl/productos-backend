package cl.huertohogar.productos.exception;

public class ProductoNotValidException extends RuntimeException {
    public ProductoNotValidException(String mensaje) {
        super(mensaje);
    }
}
