package cl.huertohogar.productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.huertohogar.productos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value = "SELECT * FROM producto WHERE id_categoria = ?1", nativeQuery = true)
    List<Producto> findByCategoriaCustom(Integer idCategoria); // Se llama custom por que el repo ya debe traer uno 

    @Query(value = "SELECT * FROM producto WHERE precio_producto BETWEEN ?1 AND ?2",nativeQuery = true)
    List<Producto> findByRangoPrecio(Integer precioMin , Integer precioMax);
    
}
