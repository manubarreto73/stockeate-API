package com.stockeate.api.dominio.productos.entities;

import java.util.List;

import com.stockeate.api.dominio.negocios.entities.Negocio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "categorias",
    uniqueConstraints = @UniqueConstraint(columnNames = {"descripcion", "negocio_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "categoria", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Producto> productos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

}
