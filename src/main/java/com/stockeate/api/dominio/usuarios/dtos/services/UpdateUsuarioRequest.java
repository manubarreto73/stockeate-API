package com.stockeate.api.dominio.usuarios.dtos.services;

import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateUsuarioRequest {
    
    private String nombreCompleto;

    public Usuario update(Usuario usuario) {

        usuario.setNombreCompleto(nombreCompleto);

        return usuario;
    }

    public static UpdateUsuarioRequest from(Usuario usuario) {
        return UpdateUsuarioRequest.builder()
            .nombreCompleto(usuario.getNombreCompleto())
            .build();
    }

}
