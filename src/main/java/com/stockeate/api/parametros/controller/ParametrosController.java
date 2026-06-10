package com.stockeate.api.parametros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.dtos.ParametrosResponse;
import com.stockeate.api.parametros.dtos.controller.UpdateParametrosRequest;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.service.ParametrosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parametros")
@RequiredArgsConstructor
public class ParametrosController {

    private final ParametrosService parametrosService;

    @GetMapping
    public ResponseEntity<ParametrosResponse> get(
        @AuthenticationPrincipal Usuario autenticado
    ) {
        Parametros parametros = parametrosService.getById(
            autenticado.getNegocio().getParametros().getId()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ParametrosResponse.from(parametros));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametrosResponse> update(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody UpdateParametrosRequest request
    ) {
        Parametros parametros = parametrosService.update(
            autenticado.getNegocio().getParametros().getId(),
            request.toEntity()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ParametrosResponse.from(parametros));
    }

}
