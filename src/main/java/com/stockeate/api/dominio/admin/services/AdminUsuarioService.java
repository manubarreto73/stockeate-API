package com.stockeate.api.dominio.admin.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.dtos.services.CreateUsuarioRequest;
import com.stockeate.api.dominio.usuarios.dtos.services.UpdateUsuarioRequest;
import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.repository.UsuarioRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAll () {
        return usuarioRepository.getAllUsuarios();
    }

}
