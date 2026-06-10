package com.stockeate.api.dominio.proveedores.dtos.service;

import com.stockeate.api.dominio.proveedores.entities.Proveedor;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateProveedorRequest {
    
    private String descripcion;
    private String telefono;

    public Proveedor update(Proveedor proveedor) {
        proveedor.setDescripcion(descripcion);
        proveedor.setTelefono(telefono);

        return proveedor;
    }

    public static UpdateProveedorRequest from(Proveedor proveedor) {
        return UpdateProveedorRequest.builder()
            .descripcion(proveedor.getDescripcion())
            .telefono(proveedor.getTelefono())
            .build();
    }

}
