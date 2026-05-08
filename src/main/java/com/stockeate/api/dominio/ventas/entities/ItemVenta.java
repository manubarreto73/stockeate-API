package com.stockeate.api.dominio.ventas.entities;

import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.productos.entities.Producto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items_ventas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ItemVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_venta_id")
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "precio_id")
    private Precio precio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id")
    private Venta venta;

}
