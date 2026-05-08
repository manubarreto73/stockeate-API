package com.stockeate.api.dominio.sesiones.dtos.services;

import com.stockeate.api.dominio.sesiones.entities.Usuario;

import lombok.*;

@Getter @Setter
@Builder
public class CreateUsuarioRequest {
    private String email;
    private String password;

    public static CreateUsuarioRequest from(Usuario usuario) {
        return CreateUsuarioRequest.builder()
            .email(usuario.getEmail())
            .password(usuario.getPassword())
            .build();
    }

    public Usuario toEntity() {
        return Usuario.builder()
            .email(email)
            .password(password)
            .build();
    }

}
