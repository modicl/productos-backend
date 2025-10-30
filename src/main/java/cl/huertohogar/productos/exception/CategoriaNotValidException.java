package cl.huertohogar.productos.exception;

public class CategoriaNotValidException extends RuntimeException{
    public CategoriaNotValidException(String mensaje){
        super(mensaje);
    }   
}
