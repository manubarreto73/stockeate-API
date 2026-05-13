package com.stockeate.api.dominio.clientes.dtos.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DeleteClienteRequest {
    
    @NotNull
    private Long id;

}
