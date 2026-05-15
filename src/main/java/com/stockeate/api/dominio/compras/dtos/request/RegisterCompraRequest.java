package com.stockeate.api.dominio.compras.dtos.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegisterCompraRequest {
    
    @NotNull
    private List<RegisterItemRequest> items;

}