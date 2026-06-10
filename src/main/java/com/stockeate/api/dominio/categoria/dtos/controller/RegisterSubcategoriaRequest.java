package com.stockeate.api.dominio.categoria.dtos.controller;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegisterSubcategoriaRequest {

    @NotNull
    private Long categoriaId;

    @NotBlank
    @Size(max = 100, message = "La descripción de la subcategoría no puede superar los 100 caracteres")
    private String descripcion;

    public Subcategoria toEntity() {
        return Subcategoria.builder()
            .descripcion(descripcion)
            .build();
    }

}
