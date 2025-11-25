package cl.huertohogar.productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.huertohogar.productos.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    
    @Query(value = "SELECT * FROM comentarios WHERE id_producto = ?1" ,nativeQuery = true)
    public List<Comentario> findComentarioByProductId(Integer idProducto);
}
