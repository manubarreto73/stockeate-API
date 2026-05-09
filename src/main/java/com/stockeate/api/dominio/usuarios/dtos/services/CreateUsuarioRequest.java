package com.stockeate.api.dominio.usuarios.dtos.services;

import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@Builder
public class CreateUsuarioRequest {
    private String email;
    private String nombreCompleto;
    private String password;

    public static CreateUsuarioRequest from(Usuario usuario) {
        return CreateUsuarioRequest.builder()
            .email(usuario.getEmail())
            .nombreCompleto(usuario.getNombreCompleto())
            .password(usuario.getPassword())
            .build();
    }

    public Usuario toEntity() {
        return Usuario.builder()
            .email(email)
            .nombreCompleto(nombreCompleto)
            .password(password)
            .build();
    }

}
