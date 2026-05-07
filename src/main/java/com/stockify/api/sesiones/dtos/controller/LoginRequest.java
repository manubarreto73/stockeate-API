package com.stockify.api.sesiones.dtos.controller;

import com.stockify.api.sesiones.entity.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginRequest {
    private String email;
    private String password;

    public Usuario toEntity() {
        return Usuario.builder()
            .email(email)
            .password(password)
            .build();
    }

}
