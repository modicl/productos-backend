package cl.huertohogar.productos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pais_origen")
@Schema(
    description = "Entidad que representa un país de origen de productos",
    title = "País de Origen",
    example = "{\"idPais\":1,\"nombre\":\"Chile\"}"
)
public class PaisOrigen {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_pais_origen", nullable = false)
    @Schema(
        description = "Identificador único autogenerado del país",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY,
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer idPais;

    @Column(name = "nombre_pais", nullable = false)
    @Schema(
        description = "Nombre oficial del país",
        example = "Chile",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 100
    )
    private String nombre;

    @OneToMany(mappedBy = "paisOrigen")
    @JsonIgnore
    @Schema(hidden = true)
    private List<Producto> productos;
}
