package com.stockeate.api.dominio.proveedores.dtos.controller;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DeleteProveedorRequest {
    
    @NotNull
    private Long idProveedor;

}
