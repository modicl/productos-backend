package cl.huertohogar.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.huertohogar.productos.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    // No tenemos queries custom por que solo haremos CRUD de categorias!
}
