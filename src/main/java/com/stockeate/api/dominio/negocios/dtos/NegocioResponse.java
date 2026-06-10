package com.stockeate.api.dominio.negocios.dtos;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.negocios.entities.Negocio;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class NegocioResponse {
    
    private String nombreNegocio;

    private LocalDateTime fechaCreacion;

    public static NegocioResponse from(Negocio negocio) {
        return NegocioResponse.builder()
            .nombreNegocio(negocio.getNombreNegocio())
            .fechaCreacion(negocio.getFechaCreacion())
            .build();
    }

}
