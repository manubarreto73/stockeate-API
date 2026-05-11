package com.stockeate.api.dominio.productos.dtos.service;

import com.stockeate.api.dominio.productos.entities.Producto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateProductoRequest {
    
    private String descripcion;
    private Integer stock;

    public static CreateProductoRequest from(Producto producto) {
        return CreateProductoRequest.builder()
            .descripcion(producto.getDescripcion())
            .stock(producto.getStock())
            .build();
    }

    public Producto toEntity () {
        return Producto.builder()
            .descripcion(this.descripcion)
            .stock(stock)
            .build();
    }

}