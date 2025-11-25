package cl.huertohogar.productos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.productos.dto.ComentarioRequestDTO;
import cl.huertohogar.productos.dto.ComentarioResponseDTO;
import cl.huertohogar.productos.model.Comentario;
import cl.huertohogar.productos.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/api/v1/comentarios")
@Tag(
    name = "Comentarios",
    description = """
        API REST para la gestión de comentarios de productos.
        
        Permite a los usuarios dejar reseñas y calificaciones sobre los productos,
        así como visualizar los comentarios existentes.
        """
)
public class ComentarioController {

    @Autowired
    public ComentarioService comentarioService;

    @Operation(
        summary = "Obtener comentarios por producto",
        description = "Obtiene la lista de comentarios asociados a un producto específico."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comentarios obtenidos exitosamente",
            content = @Content(mediaType = "application/json")
        )
    })
    @GetMapping("/{idProducto}")
    public ResponseEntity<List<ComentarioResponseDTO>> obtenerComentariosProducto(
            @Parameter(description = "ID del producto del cual obtener comentarios", example = "1")
            @PathVariable Integer idProducto) {
        List<ComentarioResponseDTO> comentarios = comentarioService.findComentarioByProductId(idProducto);

        return ResponseEntity.ok(comentarios);
    }

    @Operation(
        summary = "Crear un nuevo comentario",
        description = "Registra un nuevo comentario y calificación para un producto. Requiere que el producto exista."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Comentario creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Comentario.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos (calificación fuera de rango, comentario vacío, etc.)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"...\",\"message\":\"La calificación debe estar entre 1 y 5\",\"status\":400}")
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\":\"...\",\"message\":\"Producto no encontrado con id: 99\",\"status\":404}")
            )
        )
    })
    @PostMapping()
    public ResponseEntity<Comentario> crearComentario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo comentario",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = ComentarioRequestDTO.class)
                )
            )
            @RequestBody ComentarioRequestDTO comentarioDTO) {
        Comentario comentarioCreado = comentarioService.postComentario(comentarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCreado);
    }

}
