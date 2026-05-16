package com.stockeate.api.dominio.ventas.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EditItemRequest {
    
    @NotNull
    private Long ventaId;

    @NotNull
    private Long itemVentaId;

    @NotNull
    @Size(min = 0, max = 1000000, message = "La cantidad debe tener un valor entre 0 (cero) y 1.000.000 (un millón)")
    private Integer cantidad;

}
