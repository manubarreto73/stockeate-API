package com.stockeate.api.dominio.compras.entities;

import java.time.LocalDate;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.sesiones.entities.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compras")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compra_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario compradoPor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

}
