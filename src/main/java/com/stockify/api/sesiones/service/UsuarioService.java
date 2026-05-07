package com.stockify.api.sesiones.service;

import java.util.List;

import com.stockify.api.sesiones.dtos.services.CreateUsuarioRequest;
import com.stockify.api.sesiones.dtos.services.UpdateUsuarioRequest;
import com.stockify.api.sesiones.entity.Usuario;

public interface UsuarioService {
    
    List<Usuario> getAll();

    Usuario findById(Long id);

    Usuario findByEmail(String email);

    Usuario create(CreateUsuarioRequest request);

    Usuario update(Long id, UpdateUsuarioRequest request);

    void deactivate(Long id);

    void updateLastAccess(Long id);

}
