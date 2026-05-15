package com.stockeate.api.dominio.compras.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AddItemRequest {
    
    @NotNull
    private Long compraId;

    @NotNull
    private Long productoId;

    @NotNull
    @Size(min = 0, max = 1000000, message = "La cantidad debe tener un valor entre 0 (cero) y 1.000.000 (un millón)")
    private Integer cantidad;

}
