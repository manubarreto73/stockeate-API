package com.stockeate.api.dominio.ventas.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RemoveItemRequest {
    
    @NotNull
    private Long ventaId;

    @NotNull
    private Long itemVentaId;

}
