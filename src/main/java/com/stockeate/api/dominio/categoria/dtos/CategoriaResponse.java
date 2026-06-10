package com.stockeate.api.dominio.categoria.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.stockeate.api.dominio.categoria.entities.Categoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoriaResponse {

    private Long idCategoria;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private List<SubcategoriaResponse> subcategorias;

    public static CategoriaResponse from(Categoria categoria) {
        List<SubcategoriaResponse> subcategorias = categoria.getSubcategorias() == null ? List.of()
            : categoria.getSubcategorias().stream()
                .map(SubcategoriaResponse::from)
                .toList();

        return CategoriaResponse.builder()
            .idCategoria(categoria.getId())
            .descripcion(categoria.getDescripcion())
            .fechaCreacion(categoria.getFechaCreacion())
            .subcategorias(subcategorias)
            .build();
    }

}
