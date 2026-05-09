package com.stockeate.api.dominio.negocios.dtos.controller;

import com.stockeate.api.dominio.negocios.entities.Negocio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeNegocioRequest {
    
    @NotBlank
    private Long id;

    @NotBlank
    @Size(max = 100, message = "El nombre del negocio no puede superar los 100 caracteres")
    private String nombreNegocio;

    public Negocio toEntity() {
        return Negocio.builder()
            .nombreNegocio(nombreNegocio)
            .build();
    }

}
