package com.stockeate.api.dominio.sesiones.dtos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioResponse;
import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class RegistroResponse {
    
    private Long negocioId;
    private String nombreNegocio;
    private LocalDate fechaCreacionNegocio;

    private Long usuarioId;
    private String email;
    private String nombreCompleto;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime fechaCreacionUsuario;
    private RolUsuario rol;

    public static RegistroResponse from(RegistrarNegocioResponse registroResponse) {
        
        Negocio negocio = registroResponse.getNegocio();
        Usuario usuario = registroResponse.getUsuario();
        
        return RegistroResponse.builder()
            .negocioId(negocio.getId())
            .nombreNegocio(negocio.getNombreNegocio())
            .fechaCreacionNegocio(negocio.getFechaCreacion())
            .usuarioId(usuario.getId())
            .email(usuario.getEmail())
            .nombreCompleto(usuario.getNombreCompleto())
            .ultimoAcceso(usuario.getUltimoAcceso())
            .fechaCreacionUsuario(usuario.getFechaCreacion())
            .rol(usuario.getRol())
            .build();
    }

}
