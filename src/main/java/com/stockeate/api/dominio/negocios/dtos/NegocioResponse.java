package com.stockeate.api.dominio.negocios.dtos;

import java.time.LocalDate;

import com.stockeate.api.dominio.negocios.entities.Negocio;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class NegocioResponse {
    
    private Long negocioId;
    
    private String nombreNegocio;

    private LocalDate fechaCreacion;

    public static NegocioResponse from(Negocio negocio) {
        return NegocioResponse.builder()
            .negocioId(negocio.getId())
            .nombreNegocio(negocio.getNombreNegocio())
            .fechaCreacion(negocio.getFechaCreacion())
            .build();
    }

}
