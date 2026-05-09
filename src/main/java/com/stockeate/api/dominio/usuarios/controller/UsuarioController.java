package com.stockeate.api.dominio.usuarios.controller;

import com.stockeate.api.dominio.usuarios.dtos.controller.UsuarioResponse;
import com.stockeate.api.dominio.usuarios.dtos.controller.usuarios.ChangePassRequest;
import com.stockeate.api.dominio.usuarios.dtos.controller.usuarios.ChangeRolRequest;
import com.stockeate.api.dominio.usuarios.dtos.controller.usuarios.RegisterRequest;
import com.stockeate.api.dominio.usuarios.dtos.controller.usuarios.ChangeUsuarioRequest;
import com.stockeate.api.dominio.usuarios.dtos.controller.usuarios.DeleteUsuarioRequest;
import com.stockeate.api.dominio.usuarios.dtos.services.CreateUsuarioRequest;
import com.stockeate.api.dominio.usuarios.dtos.services.UpdateUsuarioRequest;
import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.service.UsuarioService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado
    ) {
        List<Usuario> usuarios = usuarioService.getByNegocio(autenticado.getNegocio());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(usuarios.stream()
                .map(UsuarioResponse::from)
                .toList()
            );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> create (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterRequest request
    ) {
        Usuario usuarioRequest = request.toEntity();

        Usuario usuario = usuarioService.create(autenticado.getNegocio(), CreateUsuarioRequest.from(usuarioRequest));

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UsuarioResponse.from(usuario));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> update (
        @AuthenticationPrincipal Usuario autentiado,
        @Valid @RequestBody ChangeUsuarioRequest request
    ) {
        Usuario usuarioRequest = request.toEntity();

        Usuario usuario = usuarioService.update(autentiado.getNegocio(), request.getId(), UpdateUsuarioRequest.from(usuarioRequest));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(UsuarioResponse.from(usuario));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete (
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteUsuarioRequest request
    ) {
        usuarioService.deactivate(autenticado.getNegocio(), request.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rol")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> changeRol (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody ChangeRolRequest request
    ) {
        Usuario usuario = usuarioService.changeRol(autenticado.getNegocio(), request.getId(), request.getRol());
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(UsuarioResponse.from(usuario));
    }

    @PutMapping("/password")
    public ResponseEntity<UsuarioResponse> changePassword (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody ChangePassRequest request
    ) {
        isUserOrHimself(autenticado, request.getId());

        Usuario usuario = usuarioService.changePassword(autenticado.getNegocio(), request.getId(), request.getPassword());
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(UsuarioResponse.from(usuario));
    }

    private void isUserOrHimself (Usuario autenticado, Long id) {
        if (!autenticado.getRol().equals(RolUsuario.ADMIN) && !autenticado.getId().equals(id))
            throw new AccessDeniedException("No tenés permisos para realizar esta operación");
    }

}