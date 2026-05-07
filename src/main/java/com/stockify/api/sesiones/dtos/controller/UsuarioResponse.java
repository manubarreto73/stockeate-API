package com.stockify.api.sesiones.dtos.controller;

import java.time.LocalDateTime;

import com.stockify.api.sesiones.entity.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long usuarioId;
    private String email;
    private LocalDateTime ultimoAcceso;

    public static UsuarioResponse from(Usuario usuario) {
        return UsuarioResponse.builder()
            .usuarioId(usuario.getId())
            .email(usuario.getEmail())
            .ultimoAcceso(usuario.getUltimoAcceso())
            .build();
    }
}
