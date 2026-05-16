package com.stockeate.api.dominio.clientes.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DeleteClienteRequest {
    
    @NotNull
    private Long idCliente;

}
