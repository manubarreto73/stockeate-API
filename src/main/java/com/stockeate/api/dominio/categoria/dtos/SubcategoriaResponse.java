package com.stockeate.api.dominio.categoria.dtos;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SubcategoriaResponse {

    private Long idSubcategoria;
    private String descripcion;
    private LocalDateTime fechaCreacion;

    public static SubcategoriaResponse from(Subcategoria subcategoria) {
        return SubcategoriaResponse.builder()
            .idSubcategoria(subcategoria.getId())
            .descripcion(subcategoria.getDescripcion())
            .fechaCreacion(subcategoria.getFechaCreacion())
            .build();
    }

}
