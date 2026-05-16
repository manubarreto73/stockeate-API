package com.stockeate.api.dominio.usuarios.dtos.controller.usuarios;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DeleteUsuarioRequest {
    
    @NotNull
    private Long idUsuario;
}
