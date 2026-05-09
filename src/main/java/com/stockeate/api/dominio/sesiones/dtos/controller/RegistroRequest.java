package com.stockeate.api.dominio.sesiones.dtos.controller;

import com.stockeate.api.dominio.negocios.dtos.service.CreateNegocioRequest;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioRequest;
import com.stockeate.api.dominio.usuarios.dtos.services.CreateUsuarioRequest;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class RegistroRequest {
    
    private String nombreNegocio;

    private String email;

    private String nombreCompleto;

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
