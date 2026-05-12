package com.stockeate.api.dominio.proveedores.dtos.controller;

import com.stockeate.api.dominio.proveedores.entities.Proveedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeProveedorRequest {
    
    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    public Proveedor toEntity() {
        return Proveedor.builder()
            .descripcion(descripcion)
            .build();
    }

}
