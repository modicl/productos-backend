package cl.huertohogar.productos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    description = "Entidad que representa una categoría de productos en el sistema",
    title = "Categoría",
    example = "{\"idCategoria\":1,\"nombreCategoria\":\"Verduras\",\"descripcionCategoria\":\"Verduras frescas y orgánicas de temporada\"}"
)
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    @Schema(
        description = "Identificador único autogenerado de la categoría",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY,
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer idCategoria;

    @Column(name = "nombre_categoria", nullable = false)
    @Schema(
        description = "Nombre de la categoría",
        example = "Verduras",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 100
    )
    private String nombreCategoria;

    @Column(name = "descripcion_categoria", nullable = false)
    @Schema(
        description = "Descripción detallada de la categoría y tipos de productos que incluye",
        example = "Verduras frescas y orgánicas de temporada, cultivadas localmente",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 500
    )
    private String descripcionCategoria;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    @Schema(hidden = true)
    private List<Producto> productos;
}
