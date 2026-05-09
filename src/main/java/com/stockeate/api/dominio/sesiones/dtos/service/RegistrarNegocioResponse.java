package com.stockeate.api.dominio.sesiones.dtos.service;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegistrarNegocioResponse {
    
    private Negocio negocio;    

    private Usuario usuario;

}
