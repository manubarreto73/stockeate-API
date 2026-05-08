package com.stockeate.api.dominio.ventas.entities;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.formasDePago.entities.FormaDePago;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.sesiones.entities.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ventas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_id")
    private FormaDePago formaDePago;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario vendidoPor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

}