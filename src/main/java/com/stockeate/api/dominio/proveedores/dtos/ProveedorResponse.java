package com.stockeate.api.dominio.proveedores.dtos;

import com.stockeate.api.dominio.proveedores.entities.Proveedor;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProveedorResponse {
    
    private Long idProveedor;
    private String descripcion;
    private String telefono;

    public static ProveedorResponse from (Proveedor proveedor) {
        return ProveedorResponse.builder()
            .idProveedor(proveedor.getId())
            .descripcion(proveedor.getDescripcion())
            .telefono(proveedor.getTelefono())
            .build();
    }

}
