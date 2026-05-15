package com.stockeate.api.dominio.compras.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

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

    @Column(name = "fecha_carga", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean recibida;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion;
    //FIXME - fijarse si agregar una fecha estimada de recepcion o usar esta cuando no esté recibida aún

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ItemCompra> items;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario compradoPor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

}
