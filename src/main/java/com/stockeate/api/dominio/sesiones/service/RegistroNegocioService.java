package com.stockeate.api.dominio.sesiones.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.negocios.service.NegocioService;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioRequest;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioResponse;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistroNegocioService {
    
    private final NegocioService negocioService;
    private final UsuarioService usuarioService;

    @Transactional
    public RegistrarNegocioResponse registrar (RegistrarNegocioRequest request) {

        Negocio negocio = negocioService.create(request.getRequestNegocio());

        Usuario usuario = usuarioService.createAdmin(negocio, request.getRequestUsuario());

        return new RegistrarNegocioResponse(negocio, usuario);

    }

}
