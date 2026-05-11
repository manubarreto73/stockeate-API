package com.stockeate.api.dominio.categoria.dtos.controller;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DeleteCategoriaRequest {
    
    @NotNull
    private Long id;

}
