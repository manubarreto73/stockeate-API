package com.stockify.api.sesiones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.stockify.api.common.security.JwtService;
import com.stockify.api.sesiones.dtos.controller.LoginRequest;
import com.stockify.api.sesiones.dtos.controller.LoginResponse;
import com.stockify.api.sesiones.dtos.controller.UsuarioResponse;
import com.stockify.api.sesiones.dtos.services.CreateUsuarioRequest;
import com.stockify.api.sesiones.entity.Usuario;
import com.stockify.api.sesiones.service.UsuarioService;

import lombok.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SesionController {
    
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody CreateUsuarioRequest request) {
        Usuario usuario = usuarioService.create(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UsuarioResponse.from(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Login request - email: " + request.getEmail());
        System.out.println("Login request - password: " + request.getPassword());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity
            .ok(new LoginResponse(token));
    }

}
