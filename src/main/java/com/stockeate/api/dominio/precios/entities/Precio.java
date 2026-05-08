package com.stockeate.api.dominio.precios.entities;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.productos.entities.Producto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "precios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Precio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "precio_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime desde;

    @Column()
    private LocalDateTime hasta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
