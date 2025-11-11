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
@Schema(
    description = "Entidad que representa un producto en el sistema de HuertoHogar",
    title = "Producto",
    example = "{\"idProducto\":1,\"nombreProducto\":\"Tomate Cherry Orgánico\",\"categoria\":{\"idCategoria\":1,\"nombreCategoria\":\"Verduras\",\"descripcionCategoria\":\"Verduras frescas y orgánicas\"},\"descripcionProducto\":\"Tomates cherry frescos y orgánicos cultivados sin pesticidas\",\"precioProducto\":2500,\"stockProducto\":150,\"paisOrigen\":{\"idPais\":1,\"nombre\":\"Chile\"},\"imagenUrl\":\"https://ejemplo.com/tomate-cherry.jpg\"}"
)
public class Producto {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    @Schema(
        description = "Identificador único autogenerado del producto",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY,
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer idProducto;

    @Column(name = "nombre_producto", nullable = false)
    @Schema(
        description = "Nombre comercial del producto",
        example = "Tomate Cherry Orgánico",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 255
    )
    private String nombreProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "productos"})
    @Schema(
        description = "Categoría a la que pertenece el producto",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = Categoria.class
    )
    private Categoria categoria;

    @Column(name = "descripcion_producto", nullable = false)
    @Schema(
        description = "Descripción detallada del producto, características y beneficios",
        example = "Tomates cherry frescos y orgánicos cultivados sin pesticidas, perfectos para ensaladas",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 1000
    )
    private String descripcionProducto;

    @Column(name = "precio_producto", nullable = false)
    @Schema(
        description = "Precio del producto en pesos chilenos (CLP)",
        example = "2500",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minimum = "1"
    )
    private Integer precioProducto;

    @Column(name = "stock_producto", nullable = false)
    @Schema(
        description = "Cantidad disponible en stock del producto",
        example = "150",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minimum = "0"
    )
    private Integer stockProducto;

    @ManyToOne
    @JoinColumn(name = "id_pais_origen", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "productos"})
    @Schema(
        description = "País de origen donde se produce o cultiva el producto",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = PaisOrigen.class
    )
    private PaisOrigen paisOrigen;

    @Column(name = "imagen_url", nullable = false)
    @Schema(
        description = "URL de la imagen principal del producto",
        example = "https://ejemplo.com/imagenes/tomate-cherry.jpg",
        requiredMode = Schema.RequiredMode.REQUIRED,
        format = "uri"
    )
    private String imagenUrl;
}