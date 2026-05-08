package com.stockeate.api.dominio.compras.entities;

import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.productos.entities.Producto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items_compras")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ItemCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_compra_id")
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
    @JoinColumn(name = "compra_id")
    private Compra compra;

}