package cl.huertohogar.productos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un producto en el sistema")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    @Schema(description = "Identificador único del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idProducto;

    @Column(name = "nombre_producto", nullable = false)
    @Schema(description = "Nombre del producto", example = "Tomate Cherry Orgánico")
    private String nombreProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "Categoría del producto", example = "{\"idCategoria\": 1}")
    private Categoria categoria;

    @Column(name = "descripcion_producto", nullable = false)
    @Schema(description = "Descripción del producto", example = "Tomates cherry frescos y orgánicos")
    private String descripcionProducto;

    @Column(name = "precio_producto", nullable = false)
    @Schema(description = "Precio del producto", example = "2500")
    private Integer precioProducto;

    @Column(name = "stock_producto", nullable = false)
    @Schema(description = "Stock disponible del producto", example = "150")
    private Integer stockProducto;

    @ManyToOne
    @JoinColumn(name = "id_pais_origen", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "País de origen del producto", example = "{\"idPais\": 1}")
    private PaisOrigen paisOrigen;

    @Column(name = "imagen_url", nullable = false)
    @Schema(description = "URL de la imagen del producto", example = "https://ejemplo.com/tomate.jpg")
    private String imagenUrl;
}