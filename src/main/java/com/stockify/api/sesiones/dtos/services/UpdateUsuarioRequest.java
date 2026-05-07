package com.stockify.api.sesiones.dtos.services;

import com.stockify.api.sesiones.entity.Usuario;

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
