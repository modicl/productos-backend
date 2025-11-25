package cl.huertohogar.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para la creación de un nuevo comentario")
public class ComentarioRequestDTO {
    
    @Schema(description = "ID del producto a comentar", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idProducto;

    @Schema(description = "ID del usuario que realiza el comentario", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer usuarioId;

    @Schema(description = "Texto del comentario", example = "Muy buen producto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comentario;

    @Schema(description = "Calificación (1-5)", example = "5", minimum = "1", maximum = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer calificacion;

    @Schema(description = "Fecha del comentario", example = "2025-11-24")
    private String fecha;
}