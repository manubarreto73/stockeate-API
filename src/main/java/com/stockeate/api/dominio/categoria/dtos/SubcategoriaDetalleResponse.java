package com.stockeate.api.dominio.categoria.dtos;

import java.time.LocalDateTime;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SubcategoriaDetalleResponse {

    private Long idSubcategoria;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private Long categoriaId;
    private String descCategoria;

    public static SubcategoriaDetalleResponse from(Subcategoria subcategoria) {
        return SubcategoriaDetalleResponse.builder()
            .idSubcategoria(subcategoria.getId())
            .descripcion(subcategoria.getDescripcion())
            .fechaCreacion(subcategoria.getFechaCreacion())
            .categoriaId(subcategoria.getCategoria().getId())
            .descCategoria(subcategoria.getCategoria().getDescripcion())
            .build();
    }

}
