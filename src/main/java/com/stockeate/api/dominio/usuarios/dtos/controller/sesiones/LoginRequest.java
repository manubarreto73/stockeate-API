package com.stockeate.api.dominio.usuarios.dtos.controller.sesiones;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "El negocio es obligatorio")
    private String negocio;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

}
