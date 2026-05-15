package com.stockeate.api.dominio.compras.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MarcarComoRecibidaRequest {
    
    @NotNull
    private Long id;

}
