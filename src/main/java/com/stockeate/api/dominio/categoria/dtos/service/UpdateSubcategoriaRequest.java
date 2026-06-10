package com.stockeate.api.dominio.categoria.dtos.service;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateSubcategoriaRequest {

    private String descripcion;

    public Subcategoria update(Subcategoria subcategoria) {
        subcategoria.setDescripcion(descripcion);
        return subcategoria;
    }

    public static UpdateSubcategoriaRequest from(Subcategoria subcategoria) {
        return UpdateSubcategoriaRequest.builder()
            .descripcion(subcategoria.getDescripcion())
            .build();
    }

}
