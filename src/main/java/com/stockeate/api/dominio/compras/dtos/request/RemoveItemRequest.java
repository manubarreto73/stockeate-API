package com.stockeate.api.dominio.compras.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RemoveItemRequest {
    
    @NotNull
    private Long compraId;

    @NotNull
    private Long itemCompraId;

}
