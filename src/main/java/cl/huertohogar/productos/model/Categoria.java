package cl.huertohogar.productos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;

    @Column(name = "nombre_categoria" , nullable = false)
    private String nombreCategoria;

    @Column(name = "descripcion_categoria" , nullable = false)
    private String descripcionCategoria;
}
