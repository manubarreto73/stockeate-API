package com.stockeate.api.dominio.compras.dtos.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegisterCompraRequest {
    
    private LocalDateTime fechaRecepcion;

    @NotNull
    private List<RegisterItemRequest> items;

}