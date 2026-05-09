package com.stockeate.api.dominio.usuarios.dtos.controller.usuarios;

import com.stockeate.api.dominio.usuarios.entities.RolUsuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChangeRolRequest {
    
    @NotBlank
    private Long id;

    @NotBlank
    private RolUsuario rol;

}
