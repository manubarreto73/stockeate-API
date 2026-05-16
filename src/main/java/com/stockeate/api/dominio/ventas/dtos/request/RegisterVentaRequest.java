package com.stockeate.api.dominio.ventas.dtos.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RegisterVentaRequest {
    
    Long clienteid;
    Long formaDePagoId;

    @NotNull
    List<RegisterItemRequest> items;

}
