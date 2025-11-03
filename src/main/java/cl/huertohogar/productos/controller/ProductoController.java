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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {
    
    @Autowired
    ProductoService productoService;

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un nuevo producto en el sistema con todos sus detalles"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"2025-11-01T10:30:00\",\"message\":\"El nombre del producto es obligatorio\",\"status\":400}")
            )
        )
    })
    @PostMapping("")
    public ResponseEntity<Producto> createProducto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del producto a crear",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Producto.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de producto",
                        value = "{\"nombreProducto\":\"Tomate Cherry Orgánico\",\"categoria\":{\"idCategoria\":1},\"descripcionProducto\":\"Tomates cherry frescos y orgánicos\",\"precioProducto\":2500,\"stockProducto\":150,\"paisOrigen\":{\"idPais\":1},\"imagenUrl\":\"https://ejemplo.com/tomate.jpg\"}"
                    )
                )
            )
            @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @Operation(
        summary = "Listar todos los productos",
        description = "Obtiene la lista completa de productos registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron productos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"2025-11-01T10:30:00\",\"message\":\"No se encontraron productos\",\"status\":404}")
            )
        )
    })
    @GetMapping("")
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Busca y retorna un producto específico por su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"2025-11-01T10:30:00\",\"message\":\"Producto no encontrado con id: 999\",\"status\":404}")
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(
            @Parameter(description = "ID del producto a buscar", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @Operation(
        summary = "Actualizar producto completo",
        description = "Actualiza todos los campos de un producto existente. Requiere enviar todos los datos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(mediaType = "application/json")
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos completos del producto actualizado",
                content = @Content(
                    schema = @Schema(implementation = Producto.class),
                    examples = @ExampleObject(
                        value = "{\"nombreProducto\":\"Tomate Cherry Premium\",\"categoria\":{\"idCategoria\":1},\"descripcionProducto\":\"Descripción actualizada\",\"precioProducto\":2800,\"stockProducto\":200,\"paisOrigen\":{\"idPais\":1},\"imagenUrl\":\"https://ejemplo.com/tomate-premium.jpg\"}"
                    )
                )
            )
            @RequestBody Producto producto) {
        Producto productoActualizado = productoService.update(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(
        summary = "Actualizar producto parcialmente",
        description = "Actualiza solo los campos especificados de un producto. No es necesario enviar todos los datos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> partialUpdateProducto(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Campos a actualizar (solo los que se envíen serán modificados)",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Actualizar solo precio y stock",
                        value = "{\"precioProducto\":3000,\"stockProducto\":100}"
                    )
                )
            )
            @RequestBody Producto producto) {
        Producto productoActualizado = productoService.partialUpdate(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(
        summary = "Eliminar producto",
        description = "Elimina permanentemente un producto del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(mediaType = "application/json")
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Buscar productos por categoría",
        description = "Obtiene todos los productos que pertenecen a una categoría específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron productos para esa categoría",
            content = @Content(mediaType = "application/json")
        )
    })
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Producto>> getProductosPorCategoria(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findByCategoriaCustom(id));
    }

    @Operation(
        summary = "Buscar productos por rango de precio",
        description = "Obtiene productos cuyo precio esté dentro del rango especificado (mín-máx)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron productos en ese rango",
            content = @Content(mediaType = "application/json")
        )
    })
    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> getProductosPorRangoPrecio(
            @Parameter(description = "Precio mínimo", example = "1000")
            @RequestParam Integer min,
            @Parameter(description = "Precio máximo", example = "5000")
            @RequestParam Integer max) {
        return ResponseEntity.ok(productoService.findByRangoPrecio(min, max));
    }
}
