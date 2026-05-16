package com.stockeate.api.dominio.ventas.dtos.command;

import java.util.List;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.ventas.dtos.request.RegisterItemRequest;
import com.stockeate.api.dominio.ventas.entities.Venta;

import lombok.Value;

@Value
public class CreateVentaCommand {
    private Negocio negocio;
    private Usuario vendidoPor;
    private Long clienteId;
    private Long formaDePagoId;
    private List<RegisterItemRequest> items;

    public Venta toEntity() {
        return Venta.builder()
        .negocio(negocio)
        .vendidoPor(vendidoPor) 
        .build();
    }
}
