package com.stockeate.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stockeate.api.dominio.sesiones.entities.Usuario;
import com.stockeate.api.dominio.sesiones.repository.UsuarioRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario user = userRepository.findByEmailAndActivoTrue(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Acá iría la conversión de roles a GrantedAuthority

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of()
        );
    }
}
