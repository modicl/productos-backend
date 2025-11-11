package cl.huertohogar.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.productos.config.RequireRole;
import cl.huertohogar.productos.model.Categoria;
import cl.huertohogar.productos.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/categorias")
@Tag(
    name = "Categorías",
    description = """
        API REST para la gestión de categorías de productos.
        
        Las categorías permiten organizar y clasificar los productos del catálogo,
        facilitando la navegación y búsqueda de productos por tipo.
        
        **Permisos:**
        - 🔓 GET: Público (sin autenticación)
        - 🔒 POST/PUT/PATCH/DELETE: Requiere autenticación y rol ADMIN
        """
)
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @Operation(
        summary = "Listar todas las categorías",
        description = "Obtiene la lista completa de categorías registradas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron categorías",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"2025-11-01T10:30:00\",\"message\":\"No se encontraron categorias\",\"status\":404}")
            )
        )
    })
    @GetMapping("")
    public ResponseEntity<List<Categoria>> getCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @Operation(
        summary = "Obtener categoría por ID",
        description = "Busca y retorna una categoría específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }
    
    @Operation(
        summary = "Crear nueva categoría",
        description = "Registra una nueva categoría en el sistema. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o no proporcionado",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Acceso denegado - Requiere rol ADMIN",
            content = @Content(mediaType = "application/json")
        )
    })
    @PostMapping("")
    @RequireRole({"ADMIN"})
    public ResponseEntity<Categoria> createCategoria(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la categoría a crear",
                content = @Content(
                    schema = @Schema(implementation = Categoria.class),
                    examples = @ExampleObject(
                        value = "{\"nombreCategoria\":\"Verduras\",\"descripcionCategoria\":\"Verduras frescas y orgánicas\"}"
                    )
                )
            )
            @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria));
    }

    @Operation(
        summary = "Actualizar categoría completa",
        description = "Actualiza todos los campos de una categoría existente. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o no proporcionado",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Acceso denegado - Requiere rol ADMIN",
            content = @Content(mediaType = "application/json")
        )
    })
    @PutMapping("/{id}")
    @RequireRole({"ADMIN"})
    public ResponseEntity<Categoria> putCategoria(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos completos de la categoría",
                content = @Content(
                    examples = @ExampleObject(
                        value = "{\"nombreCategoria\":\"Frutas Tropicales\",\"descripcionCategoria\":\"Frutas exóticas importadas\"}"
                    )
                )
            )
            @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.update(id, categoria));
    }

    @Operation(
        summary = "Actualizar categoría parcialmente",
        description = "Actualiza solo los campos enviados de una categoría. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada parcialmente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o no proporcionado",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Acceso denegado - Requiere rol ADMIN",
            content = @Content(mediaType = "application/json")
        )
    })
    @PatchMapping("/{id}")
    @RequireRole({"ADMIN"})
    public ResponseEntity<Categoria> patchCategoria(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Campos a actualizar",
                content = @Content(
                    examples = @ExampleObject(
                        value = "{\"descripcionCategoria\":\"Nueva descripción actualizada\"}"
                    )
                )
            )
            @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.patch(id, categoria));
    }

    @Operation(
        summary = "Eliminar categoría",
        description = "Elimina permanentemente una categoría del sistema. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o no proporcionado",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Acceso denegado - Requiere rol ADMIN",
            content = @Content(mediaType = "application/json")
        )
    })
    @DeleteMapping("/{id}")
    @RequireRole({"ADMIN"})
    public ResponseEntity<Void> deleteCategoria(
            @Parameter(description = "ID de la categoría a eliminar", example = "1")
            @PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
