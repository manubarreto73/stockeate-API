package com.stockeate.api.dominio.proveedores.controller;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.proveedores.dtos.ProveedorResponse;
import com.stockeate.api.dominio.proveedores.dtos.controller.ChangeProveedorRequest;
import com.stockeate.api.dominio.proveedores.dtos.controller.DeleteProveedorRequest;
import com.stockeate.api.dominio.proveedores.dtos.controller.RegisterProveedorRequest;
import com.stockeate.api.dominio.proveedores.dtos.service.CreateProveedorRequest;
import com.stockeate.api.dominio.proveedores.dtos.service.UpdateProveedorRequest;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;
import com.stockeate.api.dominio.proveedores.services.ProveedorService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorControler {
    
    private final ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<Page<ProveedorResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "descripcion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, Constantes.PAGE_SIZE, sort);
        Page<Proveedor> proveedores = proveedorService.getAll(autenticado.getNegocio(), pageable);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(proveedores.map(ProveedorResponse::from));
    }

    @PostMapping
    public ResponseEntity<ProveedorResponse> create(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody RegisterProveedorRequest request
    ) {
        Proveedor proveedor = proveedorService.create(
            autenticado.getNegocio(), 
            CreateProveedorRequest.from(request.toEntity())
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProveedorResponse.from(proveedor));
    }

    @PutMapping
    public ResponseEntity<ProveedorResponse> update(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody ChangeProveedorRequest request
    ) {
        Proveedor proveedor = proveedorService.update(
            autenticado.getNegocio(),
            request.getId(),
            UpdateProveedorRequest.from(request.toEntity())
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProveedorResponse.from(proveedor));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteProveedorRequest request
    ) {
        proveedorService.deactivate(autenticado.getNegocio(), request.getId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

}
