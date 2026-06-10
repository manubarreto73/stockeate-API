package com.stockeate.api.dominio.categoria.dtos.service;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateSubcategoriaRequest {

    private String descripcion;

    public static CreateSubcategoriaRequest from(Subcategoria subcategoria) {
        return CreateSubcategoriaRequest.builder()
            .descripcion(subcategoria.getDescripcion())
            .build();
    }

    public Subcategoria toEntity() {
        return Subcategoria.builder()
            .descripcion(descripcion)
            .build();
    }

}
