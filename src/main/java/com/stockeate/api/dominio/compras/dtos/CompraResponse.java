package com.stockeate.api.dominio.compras.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.stockeate.api.dominio.compras.entities.Compra;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CompraResponse {
    
    private Long compraId;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;
    private String nombreUsuario;
    private Boolean recibida;
    private LocalDateTime fechaRecepcion;
    private List<ItemCompraResponse> items;

    public static CompraResponse from (Compra compra) {
        return CompraResponse.builder()
            .compraId(compra.getId())
            .fechaCreacion(compra.getFechaCreacion())
            .usuarioId(compra.getCompradoPor().getId())
            .nombreUsuario(compra.getCompradoPor().getNombreCompleto())
            .recibida(compra.getRecibida())
            .fechaRecepcion(compra.getFechaRecepcion())
            .items(compra.getItems().stream()
                    .map(item -> ItemCompraResponse.from(item, compra))
                    .toList())
            .build();
    }

}
