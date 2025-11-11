package cl.huertohogar.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.productos.config.RequireRole;
import cl.huertohogar.productos.model.PaisOrigen;
import cl.huertohogar.productos.service.PaisOrigenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/paises")
@Tag(
    name = "Países de Origen",
    description = """
        API REST para la gestión de países de origen de productos.
        
        Los países de origen permiten identificar la procedencia geográfica de cada producto,
        información importante para trazabilidad y preferencias de consumo local.
        
        **Permisos:**
        - 🔓 GET: Público (sin autenticación)
        - 🔒 POST/PUT/PATCH/DELETE: Requiere autenticación y rol ADMIN
        """
)
public class PaisOrigenController {

    @Autowired
    PaisOrigenService paisOrigenService;

    @Operation(
        summary = "Listar todos los países",
        description = "Obtiene la lista completa de países de origen registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron países",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"2025-11-01T10:30:00\",\"message\":\"No se encontraron países de origen\",\"status\":404}")
            )
        )
    })
    @GetMapping("")
    public ResponseEntity<List<PaisOrigen>> getAllPaises() {
        return ResponseEntity.ok(paisOrigenService.findAll());
    }

    @Operation(
        summary = "Obtener país por ID",
        description = "Busca y retorna un país de origen específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País encontrado"),
        @ApiResponse(responseCode = "404", description = "País no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaisOrigen> getPaisById(
            @Parameter(description = "ID del país", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(paisOrigenService.findById(id));
    }

    @Operation(
        summary = "Crear nuevo país",
        description = "Registra un nuevo país de origen en el sistema. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "País creado exitosamente"),
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
    public ResponseEntity<PaisOrigen> createPais(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del país a crear",
                content = @Content(
                    schema = @Schema(implementation = PaisOrigen.class),
                    examples = @ExampleObject(
                        value = "{\"nombre\":\"Chile\"}"
                    )
                )
            )
            @RequestBody PaisOrigen pais) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paisOrigenService.save(pais));
    }

    @Operation(
        summary = "Actualizar país completo",
        description = "Actualiza todos los campos de un país existente. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País actualizado"),
        @ApiResponse(responseCode = "404", description = "País no encontrado"),
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
    public ResponseEntity<PaisOrigen> updatePais(
            @Parameter(description = "ID del país", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos completos del país",
                content = @Content(
                    examples = @ExampleObject(
                        value = "{\"nombre\":\"Argentina\"}"
                    )
                )
            )
            @RequestBody PaisOrigen pais) {
        return ResponseEntity.ok(paisOrigenService.update(id, pais));
    }

    @Operation(
        summary = "Actualizar país parcialmente",
        description = "Actualiza solo los campos enviados de un país. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País actualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "País no encontrado"),
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
    public ResponseEntity<PaisOrigen> patchPais(
            @Parameter(description = "ID del país", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Campos a actualizar",
                content = @Content(
                    examples = @ExampleObject(
                        value = "{\"nombre\":\"Perú\"}"
                    )
                )
            )
            @RequestBody PaisOrigen pais) {
        return ResponseEntity.ok(paisOrigenService.patch(id, pais));
    }

    @Operation(
        summary = "Eliminar país",
        description = "Elimina permanentemente un país del sistema. **Requiere rol ADMIN**"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "País eliminado"),
        @ApiResponse(responseCode = "404", description = "País no encontrado"),
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
    public ResponseEntity<Void> deletePais(
            @Parameter(description = "ID del país a eliminar", example = "1")
            @PathVariable Integer id) {
        paisOrigenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
