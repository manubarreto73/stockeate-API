package com.stockeate.api.dominio.productos.dtos.controller;

import com.stockeate.api.dominio.productos.entities.Producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegisterProductoRequest {
    
    @NotBlank
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    private Integer stock;

    private Long categoriaId;

    private Long proveedorId;

    public Producto toEntity() {
        return Producto.builder()
            .descripcion(this.descripcion)
            .stock(stock)
            .build();
    }

}
