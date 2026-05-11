package com.stockeate.api.dominio.categoria.dtos.service;

import com.stockeate.api.dominio.categoria.entities.Categoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateCategoriaRequest {
    
    private String descripcion;

    public Categoria update (Categoria categoria) {

        categoria.setDescripcion(descripcion);

        return categoria;
    }

    public Boolean hasChanges(Categoria categoria) {
        return !(categoria.getDescripcion().equals(descripcion));
    }

    public static UpdateCategoriaRequest from(Categoria categoria) {
        return UpdateCategoriaRequest.builder()
            .descripcion(categoria.getDescripcion())
            .build();
    }

}
