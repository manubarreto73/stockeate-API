package com.stockeate.api.dominio.usuarios.dtos.controller.usuarios;

import com.stockeate.api.dominio.usuarios.entities.RolUsuario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeRolRequest {
    
    @NotNull
    private Long idUsuario;

    @NotNull
    private RolUsuario rol;

}
