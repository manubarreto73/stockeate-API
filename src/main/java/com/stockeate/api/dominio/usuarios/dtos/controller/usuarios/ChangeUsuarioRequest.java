package com.stockeate.api.dominio.usuarios.dtos.controller.usuarios;

import com.stockeate.api.dominio.usuarios.entities.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeUsuarioRequest {
    
    @NotNull
    private Long idUsuario;

    @NotBlank
    @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres")
    private String nombreCompleto;

    public Usuario toEntity() {
        return Usuario.builder()
            .nombreCompleto(nombreCompleto)
            .build();
    }

}
