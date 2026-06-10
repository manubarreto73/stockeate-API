package com.stockeate.api.parametros.dtos;

import com.stockeate.api.parametros.entities.Parametros;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ParametrosResponse {

    private Boolean unaCompraDistintosProveedores;
    private Boolean exigirFormaDePagoAlCargarVenta;
    private Boolean exigirClienteAlCargarVenta;
    private Boolean permitirVenderSinStock;
    private Boolean empleadoPuedeCargarCompras;
    private Boolean empleadoPuedeUtilizarAbm;

    public static ParametrosResponse from(Parametros parametros) {
        return ParametrosResponse.builder()
            .unaCompraDistintosProveedores(parametros.getUnaCompraDistintosProveedores())
            .exigirFormaDePagoAlCargarVenta(parametros.getExigirFormaDePagoAlCargarVenta())
            .exigirClienteAlCargarVenta(parametros.getExigirClienteAlCargarVenta())
            .permitirVenderSinStock(parametros.getPermitirVenderSinStock())
            .empleadoPuedeCargarCompras(parametros.getEmpleadoPuedeCargarCompras())
            .empleadoPuedeUtilizarAbm(parametros.getEmpleadoPuedeUtilizarAbm())
            .build();
    }

}
