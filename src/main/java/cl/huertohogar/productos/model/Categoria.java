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
@Schema(description = "Entidad que representa una categoría de productos")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    @Schema(description = "Identificador único de la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idCategoria;

    @Column(name = "nombre_categoria", nullable = false)
    @Schema(description = "Nombre de la categoría", example = "Verduras", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreCategoria;

    @Column(name = "descripcion_categoria", nullable = false)
    @Schema(description = "Descripción de la categoría", example = "Verduras frescas y orgánicas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcionCategoria;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    @Schema(hidden = true)
    private List<Producto> productos;
}
