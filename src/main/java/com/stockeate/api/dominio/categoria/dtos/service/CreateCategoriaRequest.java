package com.stockeate.api.dominio.categoria.dtos.service;

import com.stockeate.api.dominio.categoria.entities.Categoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateCategoriaRequest {
    
    private String descripcion;

    public static CreateCategoriaRequest from(Categoria categoria) {
        return CreateCategoriaRequest.builder()
            .descripcion(categoria.getDescripcion())
            .build();
    }

    public Categoria toEntity () {
        return Categoria.builder()
            .descripcion(descripcion)
            .build();
    }

}
