package com.stockeate.api.dominio.productos.dtos;

import com.stockeate.api.dominio.productos.entities.Producto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoResponse {
    
    private Long productoId;

    public static ProductoResponse from(Producto producto) {
        return ProductoResponse.builder()
            .productoId(producto.getId())
            .build();
    }

}
