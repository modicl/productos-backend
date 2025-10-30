package cl.huertohogar.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.huertohogar.productos.exception.CategoriaNotFoundException;
import cl.huertohogar.productos.exception.CategoriaNotValidException;
import cl.huertohogar.productos.exception.ProductoNotFoundException;
import cl.huertohogar.productos.model.Categoria;
import cl.huertohogar.productos.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    // CRUD Basico
    // CREATE - Crear una nueva categoria
    public Categoria save(Categoria categoria) {
        categoriaRepository.save(categoria);

        if (categoria == null) {
            throw new CategoriaNotValidException("La categoria debe contener los datos necesarios");
        }

        if(categoria.getNombreCategoria().isEmpty()){
            throw new CategoriaNotValidException("La categoria debe contener almenos un nombre.");
        }

        return categoria;
    }

    // READ - Obtener categorias por ID

    public Categoria findById(Integer id){
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
        
        return categoria;
    }

    // READ - Obtener todas las categorias

    public List<Categoria> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            throw new CategoriaNotFoundException("No se encontraron categorias");
        }
        return categorias;

    }

    // UPDATE - Actualizar una categoria existente

    public Categoria update(Integer id, Categoria categoriaActualizada) {
        Categoria catExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No se encontro categoria con esa id : " + id));

        if (categoriaActualizada.getNombreCategoria().isEmpty()) {
            throw new CategoriaNotValidException("El nombre de la categoria no puede estar vacio");
        }

        catExistente.setDescripcionCategoria(categoriaActualizada.getDescripcionCategoria());
        catExistente.setNombreCategoria(categoriaActualizada.getNombreCategoria());

        return categoriaRepository.save(categoriaActualizada);

    }

    // PATCH
    public Categoria patch(Integer id, Categoria categoriaActualizada) {
        Categoria catExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No se encontro categoria con esa id : " + id));

        if (categoriaActualizada.getNombreCategoria().isEmpty()) {
            throw new CategoriaNotValidException("El nombre de la categoria no puede estar vacio");
        }

        if (!categoriaActualizada.getDescripcionCategoria().isEmpty()) {
            catExistente.setDescripcionCategoria(categoriaActualizada.getDescripcionCategoria());
        }

        if (!categoriaActualizada.getNombreCategoria().isEmpty()) {
            catExistente.setNombreCategoria(categoriaActualizada.getNombreCategoria());
        }

        return categoriaRepository.save(catExistente);

    }

    // DELETE - Eliminar una categoria

    public void delete(Integer id) {
        Categoria catExistente = findById(id);
        categoriaRepository.delete(catExistente);
    }
}
