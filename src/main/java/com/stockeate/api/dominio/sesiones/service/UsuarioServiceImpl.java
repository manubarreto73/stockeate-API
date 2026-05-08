package com.stockeate.api.dominio.sesiones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.sesiones.dtos.services.CreateUsuarioRequest;
import com.stockeate.api.dominio.sesiones.dtos.services.UpdateUsuarioRequest;
import com.stockeate.api.dominio.sesiones.entities.Usuario;
import com.stockeate.api.dominio.sesiones.repository.UsuarioRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAll () {
        return usuarioRepository.findByActivoTrue();
    }

    public Usuario findById (Long id) {
        return usuarioRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() -> new BusinessException("Usuario no encontrado con id " + id));
    }

    public Usuario findByEmail (String email) {
        return usuarioRepository.findByEmailAndActivoTrue(email)
            .orElseThrow(() -> new BusinessException("Usuario no encontrado con email " + email));
    }

    @Transactional
    public Usuario create (CreateUsuarioRequest request) {
        if (usuarioRepository.existsByEmailAndActivoTrue(request.getEmail()))
            throw new BusinessException("Ya existe un usuario con el email " + request.getEmail());

        

        Usuario usuario = request.toEntity();

        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setActivo(true);
        usuario.setUltimoAcceso(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update (Long id, UpdateUsuarioRequest request) {
        Usuario usuario = findById(id);

        return usuarioRepository.save(request.update(usuario));
    }

    @Transactional
    public void deactivate (Long id) {
        Usuario usuario = findById(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void updateLastAccess(Long id) {
        Usuario usuario = findById(id);
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

}
