package cl.huertohogar.productos.dto;

import cl.huertohogar.productos.model.Producto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


 // Datos enriquecidos... devuelve datos del usuario a traves de OpenFeign
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta con información enriquecida del comentario")
public class ComentarioResponseDTO {

    @Schema(description = "Identificador del comentario", example = "1")
    private Integer idComentario;

    @Schema(description = "Datos del usuario que realizó el comentario")
    private UsuarioDTO usuario;
    
    @Schema(description = "Texto del comentario", example = "Excelente calidad")
    private String comentario;

    @Schema(description = "Calificación otorgada", example = "5")
    private Integer calificacion;

    @Schema(description = "Fecha del comentario", example = "2025-11-24")
    private String fecha;
}
