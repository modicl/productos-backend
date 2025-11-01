package cl.huertohogar.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.huertohogar.productos.model.PaisOrigen;

@Repository
public interface PaisOrigenRepository extends JpaRepository<PaisOrigen, Integer>{
    
}
