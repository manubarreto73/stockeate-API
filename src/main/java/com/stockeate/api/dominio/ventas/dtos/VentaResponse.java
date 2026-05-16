package com.stockeate.api.dominio.ventas.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.stockeate.api.dominio.ventas.entities.Venta;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VentaResponse {
    
    private Long ventaId;
    private LocalDateTime fechaHora;
    private Long usuarioId;
    private String nombreUsuario;
    private Long clienteId;
    private String nombreCliente;
    private Long formaDePagoId;
    private String formaDePago;
    private Boolean recibida;
    private LocalDateTime fechaRecepcion;
    private List<ItemVentaResponse> items;

    public static VentaResponse from (Venta venta) {
        return VentaResponse.builder()
            .ventaId(venta.getId())
            .fechaHora(venta.getFechaHora())
            .usuarioId(venta.getVendidoPor().getId())
            .nombreUsuario(venta.getVendidoPor().getNombreCompleto())
            .clienteId(venta.getCliente().getId())
            .nombreCliente(venta.getCliente().getNombreCompleto())
            .formaDePagoId(venta.getFormaDePago().getId())
            .formaDePago(venta.getFormaDePago().getDescripcion())
            .items(venta.getItems().stream()
                    .map(item -> ItemVentaResponse.from(item, venta))
                    .toList())
            .build();
    }

}
