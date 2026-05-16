package com.stockeate.api.dominio.ventas.dtos;

import java.math.BigDecimal;

import com.stockeate.api.dominio.ventas.entities.ItemVenta;
import com.stockeate.api.dominio.ventas.entities.Venta;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemVentaResponse {
    
    private Long itemId;
    private Long ventaId;
    private Long productoId;
    private String descProducto;
    private BigDecimal precio;
    private Integer cantidad;

    public static ItemVentaResponse from (ItemVenta item, Venta venta) {
        return ItemVentaResponse.builder()
            .itemId(item.getId())
            .ventaId(venta.getId())
            .productoId(item.getProducto().getId())
            .descProducto(item.getProducto().getDescripcion())
            .precio(item.getPrecio().getMonto())
            .cantidad(item.getCantidad())
            .build();
    }

}
