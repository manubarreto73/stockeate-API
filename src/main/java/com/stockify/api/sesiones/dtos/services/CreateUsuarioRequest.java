package com.stockify.api.sesiones.dtos.services;

import com.stockify.api.sesiones.entity.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateUsuarioRequest {
    private String email;
    private String password;

    public Usuario toEntity() {
        return Usuario.builder()
            .email(email)
            .password(password)
            .build();
    }

}
