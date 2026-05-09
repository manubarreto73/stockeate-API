package com.stockeate.api.dominio.sesiones.dtos.service;

import com.stockeate.api.dominio.negocios.dtos.service.CreateNegocioRequest;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.dtos.services.CreateUsuarioRequest;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegistrarNegocioRequest {
    
    private CreateNegocioRequest requestNegocio;

    private CreateUsuarioRequest requestUsuario;

    public Negocio toNegocio() {
        return requestNegocio.toEntity();
    }

    public Usuario toUsuario() {
        return requestUsuario.toEntity();
    }

}
