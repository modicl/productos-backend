package cl.huertohogar.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.productos.model.Categoria;
import cl.huertohogar.productos.repository.CategoriaRepository;
import cl.huertohogar.productos.service.CategoriaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    @Autowired
    CategoriaService categoriaService;



    CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // GET - Obtiene todas las categorias
    @GetMapping("")
    public ResponseEntity<List<Categoria>> getCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    // GET - Obtiene categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }
    
    
    // POST - Crea una nueva categoria
    @PostMapping("")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.save(categoria));
    }

    // PUT - Actualiza una categoria completa
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> putCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {

        Categoria catActualizada = categoriaService.update(id, categoria);


        return ResponseEntity.ok(catActualizada);
    }

    // PATCH - Actualiza parcialmente una categoria
    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> patchCategoria(
        @PathVariable Integer id, 
        @RequestBody Categoria categoria) {

        Categoria catActualizada = categoriaService.patch(id, categoria);
        return ResponseEntity.ok(catActualizada);
    }

    // DELETE - Elimina una categoria por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable String id) {

        categoriaRepository.deleteById(Integer.parseInt(id));
        return ResponseEntity.noContent().build();
    }
}
