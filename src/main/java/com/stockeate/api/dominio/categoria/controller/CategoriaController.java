package com.stockeate.api.dominio.categoria.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.categoria.dtos.CategoriaResponse;
import com.stockeate.api.dominio.categoria.dtos.controller.ChangeCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.DeleteCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.RegisterCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.CreateCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.UpdateCategoriaRequest;
import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.services.CategoriaService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, Constantes.PAGE_SIZE, sort);
        Page<Categoria> categorias = categoriaService.getByNegocio(autenticado.getNegocio(), pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(categorias.map(CategoriaResponse::from));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponse> create (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterCategoriaRequest request
    ) {
        Categoria categoria = categoriaService.create(autenticado.getNegocio(), CreateCategoriaRequest.from(request.toEntity()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CategoriaResponse.from(categoria));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponse> update (
        @AuthenticationPrincipal Usuario autentiado,
        @Valid @RequestBody ChangeCategoriaRequest request
    ) {
        Categoria categoriaRequest = request.toEntity();

        Categoria categoria = categoriaService.update(autentiado.getNegocio(), request.getIdCategoria(), UpdateCategoriaRequest.from(categoriaRequest));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CategoriaResponse.from(categoria));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete (
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteCategoriaRequest request
    ) {
        categoriaService.deactivate(autenticado.getNegocio(), request.getIdCategoria());
        return ResponseEntity.ok().build();
    }
    
}