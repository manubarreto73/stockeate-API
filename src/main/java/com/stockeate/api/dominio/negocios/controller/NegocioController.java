package com.stockeate.api.dominio.negocios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.negocios.service.NegocioService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.negocios.dtos.NegocioResponse;
import com.stockeate.api.dominio.negocios.dtos.controller.ChangeNegocioRequest;
import com.stockeate.api.dominio.negocios.dtos.service.UpdateNegocioRequest;
import com.stockeate.api.dominio.negocios.entities.Negocio;

import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/api/negocios")
@RequiredArgsConstructor
public class NegocioController {

    private final NegocioService negocioService;

    @GetMapping
    public ResponseEntity<NegocioResponse> get(
        @AuthenticationPrincipal Usuario autenticado
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(NegocioResponse.from(autenticado.getNegocio()));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NegocioResponse> update(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody ChangeNegocioRequest request
    ) {
        Negocio negocioRequest = request.toEntity();
        Negocio negocio = negocioService.update(autenticado.getNegocio().getId(), UpdateNegocioRequest.from(negocioRequest));
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(NegocioResponse.from(negocio));
    }

}
