package cl.huertohogar.productos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comentarios")
@Schema(description = "Entidad que representa un comentario y calificación de un producto")
public class Comentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario", nullable = false)
    @Schema(description = "Identificador único del comentario", example = "1")
    private Integer idComentario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @Schema(description = "Producto asociado al comentario")
    private Producto producto;

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del usuario que realizó el comentario", example = "10")
    private Integer usuarioId;
    
    @Column(name = "comentario", nullable = false)
    @Schema(description = "Texto del comentario", example = "Excelente producto, muy fresco.")
    private String comentario;
    
    @Column(name = "calificacion", nullable = false)
    @Schema(description = "Calificación otorgada (1-5)", example = "5", minimum = "1", maximum = "5")
    private Integer calificacion;

    @Column(name = "fecha", nullable = false)
    @Schema(description = "Fecha de creación del comentario", example = "2025-11-24")
    private String fecha;

    
}
