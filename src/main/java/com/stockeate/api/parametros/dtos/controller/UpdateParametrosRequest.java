package com.stockeate.api.parametros.dtos.controller;

import com.stockeate.api.parametros.entities.Parametros;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateParametrosRequest {

    @NotNull
    private Boolean unaCompraDistintosProveedores;

    @NotNull
    private Boolean exigirFormaDePagoAlCargarVenta;

    @NotNull
    private Boolean exigirClienteAlCargarVenta;

    @NotNull
    private Boolean permitirVenderSinStock;

    @NotNull
    private Boolean empleadoPuedeCargarCompras;

    @NotNull
    private Boolean empleadoPuedeUtilizarAbm;

    public Parametros toEntity() {
        return Parametros.builder()
            .unaCompraDistintosProveedores(unaCompraDistintosProveedores)
            .exigirFormaDePagoAlCargarVenta(exigirFormaDePagoAlCargarVenta)
            .exigirClienteAlCargarVenta(exigirClienteAlCargarVenta)
            .permitirVenderSinStock(permitirVenderSinStock)
            .empleadoPuedeCargarCompras(empleadoPuedeCargarCompras)
            .empleadoPuedeUtilizarAbm(empleadoPuedeUtilizarAbm)
            .build();
    }

}
