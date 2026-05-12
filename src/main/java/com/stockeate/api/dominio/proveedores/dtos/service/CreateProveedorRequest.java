package com.stockeate.api.dominio.proveedores.dtos.service;

import com.stockeate.api.dominio.proveedores.entities.Proveedor;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateProveedorRequest {
    
    private String descripcion;
    private String telefono;
    
    public static CreateProveedorRequest from(Proveedor proveedor) {
        return CreateProveedorRequest.builder()
            .descripcion(proveedor.getDescripcion())
            .telefono(proveedor.getTelefono())
            .build();
    }

    public Proveedor toEntity() {
        return Proveedor.builder()
            .descripcion(descripcion)
            .telefono(telefono)
            .build();
    }

}
