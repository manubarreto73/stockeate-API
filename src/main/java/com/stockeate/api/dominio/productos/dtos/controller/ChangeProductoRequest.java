package com.stockeate.api.dominio.productos.dtos.controller;

import java.math.BigDecimal;

import com.stockeate.api.dominio.productos.entities.Producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeProductoRequest {
    
    @NotNull
    private Long idProducto;

    @NotBlank
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    @NotNull
    @Size(min = -10000, max = 10000, message = "El stock debe valer entre -10.000 (menos diez mil) y 10.000 (diez mil)")
    private Integer stock;

    private Long categoriaId;

    private Long subcategoriaId;

    private Long proveedorId;

    @NotNull
    @Size(min = -100000000, max = 100000000, message = "El precio debe valer entre -100.000.000 (menos cien millones) y 100.000.000 (cien millones)")
    private BigDecimal precio;

    public Producto toEntity() {
        return Producto.builder()
            .descripcion(this.descripcion)
            .stock(stock)
            .build();
    }

}
