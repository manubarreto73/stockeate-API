package com.stockeate.api.dominio.negocios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.negocios.service.NegocioService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.exceptions.exceptions.BusinessException;
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

    @PutMapping
    public ResponseEntity<NegocioResponse> update(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody ChangeNegocioRequest request
    ) {
        isHimself(autenticado, request.getId());
        Negocio negocioRequest = request.toEntity();
        Negocio negocio = negocioService.update(request.getId(), UpdateNegocioRequest.from(negocioRequest));
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(NegocioResponse.from(negocio));
    }

    private void isHimself (Usuario autorizado, Long id) {
        if (!autorizado.getNegocio().getId().equals(id))
            throw new BusinessException("No tenés permisos para realizar esta acción");
    }

}
