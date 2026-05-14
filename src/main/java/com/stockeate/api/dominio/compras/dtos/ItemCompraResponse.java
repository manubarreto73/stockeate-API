package com.stockeate.api.dominio.compras.dtos;

import java.math.BigDecimal;

import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ItemCompraResponse {
    
    private Long itemId;
    private Long compraId;
    private Long productoId;
    private String descProducto;
    private BigDecimal precio;
    private Integer cantidad;

    public static ItemCompraResponse from (ItemCompra item, Compra compra) {
        return ItemCompraResponse.builder()
            .itemId(item.getId())
            .compraId(compra.getId())
            .productoId(item.getProducto().getId())
            .descProducto(item.getProducto().getDescripcion())
            .precio(item.getPrecio().getMonto())
            .cantidad(item.getCantidad())
            .build();
    }

}
