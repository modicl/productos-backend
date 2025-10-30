package cl.huertohogar.productos.model;

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

public class Producto {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "nombre_producto",nullable = false)
    private String nombreProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "descripcion_producto" , nullable = false)
    private String descripcionProducto;

    @Column(name = "precio_producto", nullable = false)
    private Integer precioProducto;

    @Column(name = "stock_producto", nullable = false)
    private Integer stockProducto;

    @ManyToOne
    @JoinColumn(name = "id_pais_origen", nullable = false)
    private PaisOrigen paisOrigen;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;

}
