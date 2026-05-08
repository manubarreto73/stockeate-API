package com.stockeate.api.dominio.sesiones.service;

import java.util.List;

import com.stockeate.api.dominio.sesiones.dtos.services.CreateUsuarioRequest;
import com.stockeate.api.dominio.sesiones.dtos.services.UpdateUsuarioRequest;
import com.stockeate.api.dominio.sesiones.entities.Usuario;

public interface UsuarioService {
    
    List<Usuario> getAll();

    Usuario findById(Long id);

    Usuario findByEmail(String email);

    Usuario create(CreateUsuarioRequest request);

    Usuario update(Long id, UpdateUsuarioRequest request);

    void deactivate(Long id);

    void updateLastAccess(Long id);

}