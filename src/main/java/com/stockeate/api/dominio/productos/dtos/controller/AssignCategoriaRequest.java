package com.stockeate.api.dominio.productos.dtos.controller;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AssignCategoriaRequest {
    
    @NotNull
    private Long productoId;

    @NotNull
    private Long categoriaId;

}
