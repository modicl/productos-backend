package cl.huertohogar.productos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.huertohogar.productos.dto.ActualizacionStockRequestDTO;
import cl.huertohogar.productos.dto.ActualizacionStockResponseDTO;
import cl.huertohogar.productos.dto.ItemOrdenDTO;
import cl.huertohogar.productos.exception.ProductoNotFoundException;
import cl.huertohogar.productos.exception.ProductoNotValidException;
import cl.huertohogar.productos.exception.StockInsuficienteException;
import cl.huertohogar.productos.dto.ProductoStockDTO;
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
        if (producto.getStockCritico() != null && producto.getStockCritico() < 0) {
            throw new ProductoNotValidException("El stock crítico no puede ser negativo");
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
        if (productoActualizado.getNombreProducto() == null
                || productoActualizado.getNombreProducto().trim().isEmpty()) {
            throw new ProductoNotValidException("El nombre del producto es obligatorio");
        }
        if (productoActualizado.getPrecioProducto() == null || productoActualizado.getPrecioProducto() <= 0) {
            throw new ProductoNotValidException("El precio del producto debe ser mayor a 0");
        }
        if (productoActualizado.getStockProducto() == null || productoActualizado.getStockProducto() < 0) {
            throw new ProductoNotValidException("El stock del producto no puede ser negativo");
        }
        if (productoActualizado.getStockCritico() != null && productoActualizado.getStockCritico() < 0) {
            throw new ProductoNotValidException("El stock crítico no puede ser negativo");
        }

        productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setDescripcionProducto(productoActualizado.getDescripcionProducto());
        productoExistente.setPrecioProducto(productoActualizado.getPrecioProducto());
        productoExistente.setStockProducto(productoActualizado.getStockProducto());
        productoExistente.setStockCritico(productoActualizado.getStockCritico());
        productoExistente.setPaisOrigen(productoActualizado.getPaisOrigen());
        productoExistente.setImagenUrl(productoActualizado.getImagenUrl());

        return productoRepository.save(productoExistente);
    }

    // Acutalizacion de stock en base a una orden
    public ActualizacionStockResponseDTO actualizarStockPorOrden(ActualizacionStockRequestDTO request) {
        List<ProductoStockDTO> productosActualizados = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        try {
            for (ItemOrdenDTO item : request.getItems()) {
                if (item.getCantidad() == null || item.getCantidad() <= 0) {
                    throw new ProductoNotValidException(
                            "La cantidad del producto debe ser mayor a 0 para el producto ID: " + item.getIdProducto());

                }

                Producto producto = productoRepository.findById(item.getIdProducto())
                        .orElseThrow(() -> new ProductoNotFoundException(
                                "Producto no encontrado con id: " + item.getIdProducto()));

                // Verificamos el stock
                if (producto.getStockProducto() < item.getCantidad()) {
                    throw new StockInsuficienteException(
                            "No hay suficiente stock para el producto ID: " + item.getIdProducto());
                }

                // Actualizamos stock si todo esta bien
                for (ItemOrdenDTO itemOrden : request.getItems()) {
                    Producto productoOrden = productoRepository.findById(itemOrden.getIdProducto()).get();
                    Integer stockAnterior = productoOrden.getStockProducto();
                    Integer stockNuevo = stockAnterior - itemOrden.getCantidad();
                    productoOrden.setStockProducto(stockNuevo);
                    productoRepository.save(productoOrden);

                    // Registramos el cambio con el nuevo stock (esto lo devolveremos en el
                    // repsonse)
                    productosActualizados.add(new ProductoStockDTO(
                            productoOrden.getIdProducto(),
                            productoOrden.getNombreProducto(),
                            stockAnterior,
                            stockNuevo,
                            itemOrden.getCantidad()));

                }

            }
            return new ActualizacionStockResponseDTO(
                    true,
                    "Stock actualizado correctamente para la orden: " + request.getIdOrden(),
                    productosActualizados,
                    null);
        } catch (ProductoNotFoundException | StockInsuficienteException | ProductoNotValidException e) {
            errores.add(e.getMessage());
            throw e; // Esto provocará el rollback de la transacción
        } catch (Exception e) {
            errores.add("Error inesperado: " + e.getMessage());
            throw new RuntimeException("Error al procesar la actualización de stock", e);
        }
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

        if (productoActualizado.getStockCritico() != null) {
            if (productoActualizado.getStockCritico() < 0) {
                throw new ProductoNotValidException("El stock crítico no puede ser negativo");
            }
            productoExistente.setStockCritico(productoActualizado.getStockCritico());
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
            throw new ProductoNotFoundException(
                    "No se encontraron productos en ese rango: " + precioMin + " - " + precioMax);
        }
        return productos;
    }
}
