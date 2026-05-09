package com.stockeate.api.dominio.usuarios.dtos.controller.sesiones;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
