package cl.huertohogar.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

}
