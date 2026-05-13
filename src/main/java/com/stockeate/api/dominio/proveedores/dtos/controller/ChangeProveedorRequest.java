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

    @Size(min = 7, max = 20, message = "El telefono debe tener entre 7 y 20 caracteres")
    private String telefono;

    public Proveedor toEntity() {
        return Proveedor.builder()
            .descripcion(descripcion)
            .telefono(telefono)
            .build();
    }

}
