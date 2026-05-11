package com.stockeate.api.dominio.categoria.dtos.controller;

import com.stockeate.api.dominio.categoria.entities.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeCategoriaRequest {
    
    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 100, message = "El nombre de la categoria no puede superar los 100 caracteres")
    private String descripcion;

    public Categoria toEntity() {
        return Categoria.builder()
            .descripcion(descripcion)
            .build();
    }

}
