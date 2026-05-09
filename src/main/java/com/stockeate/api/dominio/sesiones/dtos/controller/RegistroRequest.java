package com.stockeate.api.dominio.sesiones.dtos.controller;

import com.stockeate.api.dominio.negocios.dtos.service.CreateNegocioRequest;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioRequest;
import com.stockeate.api.dominio.usuarios.dtos.services.CreateUsuarioRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class RegistroRequest {
    
    @NotBlank
    @Size(max = 100, message = "El nombre del negocio no puede superar los 100 caracteres")
    private String nombreNegocio;

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

    public RegistrarNegocioRequest toEntity() {
        CreateNegocioRequest requestNegocio = CreateNegocioRequest.builder()
            .nombreNegocio(nombreNegocio)
            .build();
        
        CreateUsuarioRequest usuarioRequest = CreateUsuarioRequest.builder()
            .email(email)
            .nombreCompleto(nombreCompleto)
            .password(password)
            .build();

        return RegistrarNegocioRequest.builder()
            .requestNegocio(requestNegocio)
            .requestUsuario(usuarioRequest)
            .build();
    }

}
