package com.stockify.api.common.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stockify.api.sesiones.entity.Usuario;
import com.stockify.api.sesiones.repository.UsuarioRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("Buscando usuario: " + username);
        userRepository.findAll().forEach(u -> 
            System.out.println("En BD: '" + u.getEmail() + "' activo: " + u.getActivo())
        );
        Usuario user = userRepository.findByEmailAndActivoTrue(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        System.out.println("Usuario encontrado: " + user.getEmail());
        System.out.println("Password hash: " + user.getPassword());

        // Acá iría la conversión de roles a GrantedAuthority

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of()
        );
    }
}
