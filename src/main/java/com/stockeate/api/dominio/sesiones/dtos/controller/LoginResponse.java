package com.stockeate.api.dominio.sesiones.dtos.controller;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginResponse {
    private String nombreNegocio;
    private Long usuarioId;
    private String email;
    private String nombreCompleto;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime fechaCreacion;
    private RolUsuario rol;
    private String token;
    private String refreshToken;

    public static LoginResponse from(Usuario usuario) {
        return LoginResponse.builder()
            .nombreNegocio(usuario.getNegocio().getNombreNegocio())
            .usuarioId(usuario.getId())
            .email(usuario.getEmail())
            .nombreCompleto(usuario.getNombreCompleto())
            .ultimoAcceso(usuario.getUltimoAcceso())
            .fechaCreacion(usuario.getFechaCreacion())
            .rol(usuario.getRol())
            .build();
    }
}
