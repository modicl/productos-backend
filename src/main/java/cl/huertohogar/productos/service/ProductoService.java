package cl.huertohogar.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.huertohogar.productos.exception.ProductoNotFoundException;
import cl.huertohogar.productos.exception.ProductoNotValidException;
import cl.huertohogar.productos.model.Producto;
import cl.huertohogar.productos.repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    // CREATE - Crear un nuevo producto
    public Producto save(Producto producto) {
        if (producto == null) {
            throw new ProductoNotValidException("El producto no puede ser nulo");
        }
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new ProductoNotValidException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecioProducto() == null || producto.getPrecioProducto() <= 0) {
            throw new ProductoNotValidException("El precio del producto debe ser mayor a 0");
        }
        if (producto.getStockProducto() == null || producto.getStockProducto() < 0) {
            throw new ProductoNotValidException("El stock del producto no puede ser negativo");
        }
        return productoRepository.save(producto);
    }

    // READ - Obtener todos los productos
    public List<Producto> findAll() {
        List<Producto> productos = productoRepository.findAll();
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("No se encontraron productos");
        }
        return productos;
    }

    // READ - Obtener producto por ID
    public Producto findById(Integer id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }

    // UPDATE - Actualizar un producto existente
    public Producto update(Integer id, Producto productoActualizado) {
        Producto productoExistente = findById(id);
        
        // Validaciones
        if (productoActualizado.getNombreProducto() == null || productoActualizado.getNombreProducto().trim().isEmpty()) {
            throw new ProductoNotValidException("El nombre del producto es obligatorio");
        }
        if (productoActualizado.getPrecioProducto() == null || productoActualizado.getPrecioProducto() <= 0) {
            throw new ProductoNotValidException("El precio del producto debe ser mayor a 0");
        }
        if (productoActualizado.getStockProducto() == null || productoActualizado.getStockProducto() < 0) {
            throw new ProductoNotValidException("El stock del producto no puede ser negativo");
        }
        
        productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setDescripcionProducto(productoActualizado.getDescripcionProducto());
        productoExistente.setPrecioProducto(productoActualizado.getPrecioProducto());
        productoExistente.setStockProducto(productoActualizado.getStockProducto());
        productoExistente.setPaisOrigen(productoActualizado.getPaisOrigen());
        productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
        
        return productoRepository.save(productoExistente);
    }

    // PATCH - Actualizar parcialmente un producto
    public Producto partialUpdate(Integer id, Producto productoActualizado) {
        Producto productoExistente = findById(id);
        
        // Solo actualiza los campos que no son nulos
        if (productoActualizado.getNombreProducto() != null) {
            if (productoActualizado.getNombreProducto().trim().isEmpty()) {
                throw new ProductoNotValidException("El nombre del producto no puede estar vacío");
            }
            productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
        }
        
        if (productoActualizado.getCategoria() != null) {
            productoExistente.setCategoria(productoActualizado.getCategoria());
        }
        
        if (productoActualizado.getDescripcionProducto() != null) {
            productoExistente.setDescripcionProducto(productoActualizado.getDescripcionProducto());
        }
        
        if (productoActualizado.getPrecioProducto() != null) {
            if (productoActualizado.getPrecioProducto() <= 0) {
                throw new ProductoNotValidException("El precio del producto debe ser mayor a 0");
            }
            productoExistente.setPrecioProducto(productoActualizado.getPrecioProducto());
        }
        
        if (productoActualizado.getStockProducto() != null) {
            if (productoActualizado.getStockProducto() < 0) {
                throw new ProductoNotValidException("El stock del producto no puede ser negativo");
            }
            productoExistente.setStockProducto(productoActualizado.getStockProducto());
        }
        
        if (productoActualizado.getPaisOrigen() != null) {
            productoExistente.setPaisOrigen(productoActualizado.getPaisOrigen());
        }
        
        if (productoActualizado.getImagenUrl() != null) {
            productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
        }
        
        return productoRepository.save(productoExistente);
    }

    // DELETE - Eliminar un producto
    public void deleteById(Integer id) {
        Producto producto = findById(id);
        productoRepository.delete(producto);
    }

    // CONSULTAS PERSONALIZADAS
    
    // Nos devuelve producto por categoria
    public List<Producto> findByCategoriaCustom(Integer idCategoria) {
        List<Producto> productos = productoRepository.findByCategoriaCustom(idCategoria);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("No se encontraron productos con la id de categoria: " + idCategoria);
        }
        return productos;
    }

    // Nos devuelve productos en un rango de precios
    public List<Producto> findByRangoPrecio(Integer precioMin, Integer precioMax) {
        List<Producto> productos = productoRepository.findByRangoPrecio(precioMin, precioMax);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("No se encontraron productos en ese rango: " + precioMin + " - " + precioMax);
        }
        return productos;
    }
}
