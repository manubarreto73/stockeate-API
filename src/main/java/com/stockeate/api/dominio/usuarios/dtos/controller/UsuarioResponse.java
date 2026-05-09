package com.stockeate.api.dominio.usuarios.dtos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long usuarioId;
    private String email;
    private String nombreCompleto;
    private LocalDateTime ultimoAcceso;
    private LocalDate fechaCreacion;
    private RolUsuario rol;

    public static UsuarioResponse from(Usuario usuario) {
        return UsuarioResponse.builder()
            .usuarioId(usuario.getId())
            .email(usuario.getEmail())
            .nombreCompleto(usuario.getNombreCompleto())
            .ultimoAcceso(usuario.getUltimoAcceso())
            .fechaCreacion(usuario.getFechaCreacion())
            .rol(usuario.getRol())
            .build();
    }
}
