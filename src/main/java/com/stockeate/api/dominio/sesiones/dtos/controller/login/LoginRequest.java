package com.stockeate.api.dominio.sesiones.dtos.controller.login;

import com.stockeate.api.dominio.sesiones.entities.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email (message = "El email no tiene el formato adecuado")
    @Size(max = 120, message = "El email no puede superar los 120 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String password;

    public Usuario toEntity() {
        return Usuario.builder()
            .email(email)
            .password(password)
            .build();
    }

}
