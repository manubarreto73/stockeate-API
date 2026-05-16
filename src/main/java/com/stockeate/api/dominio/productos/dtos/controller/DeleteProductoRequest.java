package com.stockeate.api.dominio.productos.dtos.controller;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DeleteProductoRequest {
    
    @NotNull
    private Long idProducto;

}
