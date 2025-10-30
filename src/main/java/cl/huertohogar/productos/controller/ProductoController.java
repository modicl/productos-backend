package cl.huertohogar.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.productos.model.Producto;
import cl.huertohogar.productos.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    
    @Autowired
    ProductoService productoService;

    // CREATE - Crear un nuevo producto
    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // READ - Obtener todos los productos
    @GetMapping("")
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    // READ - Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    // PUT - Actualizar un producto existente (completo)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Integer id, 
            @RequestBody Producto producto) {
        Producto productoActualizado = productoService.update(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // PATCH - Actualizar parcialmente un producto
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> partialUpdateProducto(
            @PathVariable Integer id, 
            @RequestBody Producto producto) {
        Producto productoActualizado = productoService.partialUpdate(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // DELETE - Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // CONSULTAS PERSONALIZADAS

    // Obtener productos por categoría
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Producto>> getProductosPorCategoria(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findByCategoriaCustom(id));
    }

    // Obtener productos por rango de precio
    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> getProductosPorRangoPrecio(
            @RequestParam Integer min, 
            @RequestParam Integer max) {
        return ResponseEntity.ok(productoService.findByRangoPrecio(min, max));
    }
}
