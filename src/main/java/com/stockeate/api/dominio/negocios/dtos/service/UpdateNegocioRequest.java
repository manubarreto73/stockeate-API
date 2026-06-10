package com.stockeate.api.dominio.negocios.dtos.service;

import com.stockeate.api.dominio.negocios.entities.Negocio;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateNegocioRequest {
    
    private String nombreNegocio;

    public Negocio update(Negocio negocio) {
        
        negocio.setNombreNegocio(nombreNegocio);

        return negocio;
    }

    public static UpdateNegocioRequest from(Negocio negocio) {
        return UpdateNegocioRequest.builder()
            .nombreNegocio(negocio.getNombreNegocio())
            .build();
    }

}
