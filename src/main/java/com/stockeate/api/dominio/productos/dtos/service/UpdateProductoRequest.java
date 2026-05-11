package com.stockeate.api.dominio.productos.dtos.service;

import com.stockeate.api.dominio.productos.entities.Producto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateProductoRequest {
    
    private String descripcion;

    public Producto update (Producto producto) {
        producto.setDescripcion(this.descripcion);
        return producto;
    }

    public Boolean hasChanges(Producto producto) {
        return !this.descripcion.equals(producto.getDescripcion());
    }

    public static UpdateProductoRequest from(Producto producto) {
        return UpdateProductoRequest.builder()
            .descripcion(producto.getDescripcion())
            .build();
    }

}
