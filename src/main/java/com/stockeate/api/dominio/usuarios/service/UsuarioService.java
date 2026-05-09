package com.stockeate.api.dominio.usuarios.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UsuarioService implements UserDetailsService{

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getByNegocio (Negocio negocio) {
        return usuarioRepository.findByNegocioAndActivoTrue(negocio);
    }

    public Usuario findById (Negocio negocio, Long id) {
        return usuarioRepository.findByIdAndNegocioAndActivoTrue(id, negocio)
            .orElseThrow(() -> new BusinessException("Usuario no encontrado con id " + id));
    }

    public Usuario findByEmail (String email) {
        return usuarioRepository.findByEmailAndActivoTrue(email)
            .orElseThrow(() -> new BusinessException("Usuario no encontrado con email " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByEmailAndActivoTrue(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Transactional
    public Usuario createAdmin (Negocio negocio, CreateUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail()))
            throw new BusinessException("Ya existe un usuario con el email " + request.getEmail());

        Usuario usuario = request.toEntity();

        usuario.setNegocio(negocio);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setRol(RolUsuario.ADMIN);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario create (Negocio negocio, CreateUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail()))
            throw new BusinessException("Ya existe un usuario con el email " + request.getEmail());

        Usuario usuario = request.toEntity();

        usuario.setNegocio(negocio);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setRol(RolUsuario.EMPLEADO);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update (Negocio negocio, Long id, UpdateUsuarioRequest request) {
        Usuario usuario = findById(negocio, id);
        if (!request.hasChanges(usuario))
            throw new BusinessException("La entidad enviada para actualizar no contiene cambios");
        return usuarioRepository.save(request.update(usuario));
    }

    @Transactional
    public Usuario changeRol (Negocio negocio, Long id, RolUsuario rol) {
        //No se puede darle a alguien permisos de superadmin ni de admin
        if (rol.equals(RolUsuario.SUPERADMIN) || rol.equals(RolUsuario.ADMIN))
            throw new BusinessException("Rol no permitido");

        Usuario usuario = findById(negocio, id);

        if (usuario.getRol().equals(RolUsuario.ADMIN) || usuario.getRol().equals(RolUsuario.ADMIN))
            throw new BusinessException("Rol se puede cambiar el rol del administrador");

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario changePassword (Negocio negocio, Long id, String password) {
        Usuario usuario = findById(negocio, id);

        if (password.equals(usuario.getPassword()))
            throw new BusinessException("La nueva contraseña es igual a la actual");

        usuario.setPassword(passwordEncoder.encode(password));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deactivate (Negocio negocio, Long id) {
        Usuario usuario = findById(negocio, id);
        
        if (usuario.getRol().equals(RolUsuario.SUPERADMIN) || usuario.getRol().equals(RolUsuario.ADMIN))
            throw new BusinessException("No se puede eliminar al usuario administrador");

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void updateLastAccess(Negocio negocio, Long id) {
        Usuario usuario = findById(negocio, id);
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

}
