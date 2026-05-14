package com.stockeate.api.dominio.compras.dtos.services;

import java.util.List;

import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateCompraRequest {
    
    private List<ItemCompra> items;

    public Compra toEntity() {
        return Compra.builder()
            .build();
    }

}
