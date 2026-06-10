package com.stockeate.api.dominio.sesiones.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.stockeate.api.dominio.sesiones.dtos.controller.LoginRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.LoginResponse;
import com.stockeate.api.dominio.sesiones.dtos.controller.RefreshRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.RefreshResponse;
import com.stockeate.api.dominio.sesiones.dtos.controller.RegistroRequest;
import com.stockeate.api.dominio.sesiones.dtos.controller.RegistroResponse;
import com.stockeate.api.dominio.sesiones.service.LoginAttemptsService;
import com.stockeate.api.dominio.sesiones.service.RefreshTokenService;
import com.stockeate.api.dominio.sesiones.service.RegistroNegocioService;
import com.stockeate.api.dominio.usuarios.dtos.controller.UsuarioResponse;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.service.UsuarioService;
import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.redis.RedisService;
import com.stockeate.api.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SesionController {

    private final UsuarioService usuarioService;
    private final LoginAttemptsService loginAttemptsService;
    private final RegistroNegocioService registroNegocioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RedisService redisService;

    @GetMapping
    public ResponseEntity<UsuarioResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> registro(
        @Valid @RequestBody RegistroRequest request
    ) {
        RegistroResponse response = RegistroResponse.from(registroNegocioService.registrar(request.toEntity()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletRequest httpRequest
    ) {
        String ip = clientIp(httpRequest);

        if (loginAttemptsService.estaBloqueada(ip))
            throw new BusinessException("IP bloqueada temporalmente");

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(request.getEmail());

            if (!usuario.getNegocio().getNombreNegocio().equals(request.getNegocio()))
                throw new BadCredentialsException("El usuario no pertenece a ese negocio");

            usuarioService.updateLastAccess(usuario.getNegocio(), usuario.getId());
            loginAttemptsService.limpiarIntentos(ip);

            String accessToken = jwtService.generateToken(usuario);
            String refreshToken = refreshTokenService.create(usuario.getEmail());

            LoginResponse response = LoginResponse.from(usuario);
            response.setToken(accessToken);
            response.setRefreshToken(refreshToken);

            return ResponseEntity.ok(response);
        }
        catch (BadCredentialsException e) {
            loginAttemptsService.registrarIntento(ip);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
        @Valid @RequestBody RefreshRequest request
    ) {
        String newRefreshToken = UUID.randomUUID().toString();
        String email = refreshTokenService.validateAndRotate(request.getRefreshToken(), newRefreshToken);
        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(email);
        String newAccessToken = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new RefreshResponse(newAccessToken, newRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody RefreshRequest request,
        HttpServletRequest httpRequest
    ) {
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            long ttlMinutes = jwtService.getRemainingMinutes(accessToken);
            redisService.set("blacklist:" + accessToken, "1", ttlMinutes);
        }
        refreshTokenService.revoke(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    /**
     * Si la app está detrás de un proxy/load balancer de confianza que setea
     * X-Forwarded-For, usar esa IP. De lo contrario, request.getRemoteAddr()
     * sería la IP del proxy y no la del cliente real.
     */
    private String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank())
            return forwarded.split(",")[0].trim();
        return request.getRemoteAddr();
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
