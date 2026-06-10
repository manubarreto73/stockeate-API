package com.stockeate.api.dominio.productos.entities;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.entities.Subcategoria;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
    name = "productos",
    uniqueConstraints = @UniqueConstraint(columnNames = {"descripcion", "negocio_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE productos SET activo = false WHERE producto_id = ?")
@SQLRestriction("activo = true")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean activo;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "categoria_id", nullable = true)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "subcategoria_id", nullable = true)
    private Subcategoria subcategoria;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

}
