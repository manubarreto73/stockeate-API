package com.stockeate.api.dominio.sesiones.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.stockeate.api.dominio.negocios.entities.Negocio;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(name = "nombre_completo", length = 100)
    private String nombreCompleto;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(nullable = false)
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "rol")
    private List<RolUsuario> roles;

}
