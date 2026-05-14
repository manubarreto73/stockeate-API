package com.stockeate.api.dominio.productos.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.productos.entities.Producto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoResponse {
    
    private Long productoId;
    private String descProducto;
    private Integer stock;
    private BigDecimal precio;
    private LocalDateTime fechaCreacion;
    private Long categoriaId;
    private String descCategoria;
    private Long proveedorId;
    private String descProveedor;

    public static ProductoResponse from(Producto producto, Precio precio) {
        return ProductoResponse.builder()
            .productoId(producto.getId())
            .descProducto(producto.getDescripcion())
            .stock(producto.getStock())
            .precio(precio.getMonto())
            .fechaCreacion(producto.getFechaCreacion())
            .categoriaId(producto.getCategoria() == null ? 0 : producto.getCategoria().getId())
            .descCategoria(producto.getCategoria() == null ? "" : producto.getCategoria().getDescripcion())
            .proveedorId(producto.getProveedor() == null ? 0 : producto.getProveedor().getId())
            .descProveedor(producto.getProveedor() == null ? "" : producto.getProveedor().getDescripcion())
            .build();
    }

}
