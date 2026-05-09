package com.stockeate.api.dominio.usuarios.dtos.controller.usuarios;

import com.stockeate.api.dominio.usuarios.entities.Usuario;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegisterRequest {
    
    @NotBlank(message = "El email es obligatorio")
    @Email (message = "El email no tiene el formato adecuado")
    @Size(max = 255, message = "El email no puede superar los 255 caracteres")
    private String email;

    @NotBlank(message = "La nombre de usuario es obligatorio")
    @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres")
    private String nombreCompleto;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String password;

    public Usuario toEntity() {
        return Usuario.builder()
            .email(email)
            .nombreCompleto(nombreCompleto)
            .password(password)
            .build();
    }
}
