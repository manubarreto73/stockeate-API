package com.stockeate.api.dominio.negocios.dtos.service;

import com.stockeate.api.dominio.negocios.entities.Negocio;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateNegocioRequest {
    
    private String nombreNegocio;

    public Negocio toEntity() {
        return Negocio.builder()
            .nombreNegocio(nombreNegocio)
            .build();
    }

}
