package com.stockeate.api.dominio.categoria.dtos;

import java.time.LocalDate;

import com.stockeate.api.dominio.categoria.entities.Categoria;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoriaResponse {
    
    private Long id;
    private String descripcion;
    private LocalDate fechaCreacion;

    public static CategoriaResponse from(Categoria categoria) {
        return CategoriaResponse.builder()
            .id(categoria.getId())
            .descripcion(categoria.getDescripcion())
            .fechaCreacion(categoria.getFechaCreacion())
            .build();
    }

}
