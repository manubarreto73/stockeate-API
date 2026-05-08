package com.stockeate.api.dominio.sesiones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.stockeate.api.dominio.sesiones.dtos.controller.UsuarioResponse;
import com.stockeate.api.dominio.sesiones.dtos.controller.login.LoginRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.login.LoginResponse;
import com.stockeate.api.dominio.sesiones.dtos.controller.register.RegisterRequest;
import com.stockeate.api.dominio.sesiones.dtos.services.CreateUsuarioRequest;
import com.stockeate.api.dominio.sesiones.entities.Usuario;
import com.stockeate.api.dominio.sesiones.service.UsuarioService;
import com.stockeate.api.security.JwtService;

import jakarta.validation.Valid;
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
    public ResponseEntity<UsuarioResponse> register(
        @Valid @RequestBody RegisterRequest request
    ) {
        Usuario usuario = usuarioService.create(CreateUsuarioRequest.from(request.toEntity()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UsuarioResponse.from(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request
    ) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity
            .ok(new LoginResponse(token));
    }

    @GetMapping
    public ResponseEntity<UsuarioResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UsuarioResponse.from(usuario));
    }

}
