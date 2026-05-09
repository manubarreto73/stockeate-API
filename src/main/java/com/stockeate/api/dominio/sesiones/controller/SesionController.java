package com.stockeate.api.dominio.sesiones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.stockeate.api.dominio.negocios.dtos.service.CreateNegocioRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.LoginRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.LoginResponse;
import com.stockeate.api.dominio.sesiones.dtos.controller.RegistroRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.RegistroResponse;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioRequest;
import com.stockeate.api.dominio.sesiones.dtos.service.RegistrarNegocioResponse;
import com.stockeate.api.dominio.sesiones.service.RegistroNegocioService;
import com.stockeate.api.dominio.usuarios.dtos.controller.UsuarioResponse;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.service.UsuarioService;
import com.stockeate.api.security.JwtService;

import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SesionController {
    
    private final UsuarioService usuarioService;
    private final RegistroNegocioService registroNegocioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<UsuarioResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UsuarioResponse.from(usuario));
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> registro (
        @Valid @RequestBody RegistroRequest request
    ) {
        RegistroResponse response = RegistroResponse.from(registroNegocioService.registrar(request.toEntity()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request
    ) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(request.getEmail());

        if (!usuario.getNegocio().getNombreNegocio().equals(request.getNegocio()))
            throw new BadCredentialsException("El usuario no pertenece a ese negocio");

        usuarioService.updateLastAccess(usuario.getNegocio(), usuario.getId());

        return ResponseEntity
            .ok(new LoginResponse(jwtService.generateToken(usuario)));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> forgotPassword(
        @RequestBody String email
    ) {
        //Validar que el email exista
        //Generar token con fecha de expiración
        //Guardarlo en almacenamiento local
        //Enviar correo de recuperación de clave
        //Notificar que fue enviado el correo
        return ResponseEntity.ok(false);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(
        @RequestBody String token
    ) {
        //Validar token
        //De ser valido auténticar al usuario
        //Redirigir a cambiar su contraseña
        return ResponseEntity.ok(false);
    }

}
