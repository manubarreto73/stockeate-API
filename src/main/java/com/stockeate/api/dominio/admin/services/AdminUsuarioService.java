package com.stockeate.api.dominio.admin.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAll () {
        return usuarioRepository.findAll();
    }

}
