package com.stockeate.api.dominio.sesiones.dtos.services;

import com.stockeate.api.dominio.sesiones.entities.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateUsuarioRequest {
    
    private String password;

    public Usuario update(Usuario usuario) {

        usuario.setPassword(password);

        return usuario;
    }

}
