package com.stockeate.api.dominio.categoria.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.categoria.dtos.CategoriaResponse;
import com.stockeate.api.dominio.categoria.dtos.SubcategoriaDetalleResponse;
import com.stockeate.api.dominio.categoria.dtos.SubcategoriaResponse;
import com.stockeate.api.dominio.categoria.dtos.controller.ChangeCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.ChangeSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.DeleteCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.DeleteSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.RegisterCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.controller.RegisterSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.CreateCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.CreateSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.UpdateCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.UpdateSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.entities.Subcategoria;
import com.stockeate.api.dominio.categoria.services.CategoriaService;
import com.stockeate.api.dominio.categoria.services.SubcategoriaService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.Constantes;
import com.stockeate.api.parametros.service.PermisosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;
    private final PermisosService permisosService;

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
    public ResponseEntity<CategoriaResponse> create (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterCategoriaRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        Categoria categoria = categoriaService.create(autenticado.getNegocio(), CreateCategoriaRequest.from(request.toEntity()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CategoriaResponse.from(categoria));
    }

    @PutMapping
    public ResponseEntity<CategoriaResponse> update (
        @AuthenticationPrincipal Usuario autentiado,
        @Valid @RequestBody ChangeCategoriaRequest request
    ) {
        permisosService.verificarAbm(autentiado);
        Categoria categoriaRequest = request.toEntity();

        Categoria categoria = categoriaService.update(autentiado.getNegocio(), request.getIdCategoria(), UpdateCategoriaRequest.from(categoriaRequest));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CategoriaResponse.from(categoria));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete (
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteCategoriaRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        categoriaService.deactivate(autenticado.getNegocio(), request.getIdCategoria());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subcategorias")
    public ResponseEntity<List<SubcategoriaDetalleResponse>> getSubcategorias(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(required = false) Long categoriaId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(subcategoriaService.getByNegocio(autenticado.getNegocio(), categoriaId)
                .stream().map(SubcategoriaDetalleResponse::from).toList());
    }

    @PostMapping("/subcategorias")
    public ResponseEntity<SubcategoriaResponse> createSubcategoria(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterSubcategoriaRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        Subcategoria subcategoria = subcategoriaService.create(
            autenticado.getNegocio(),
            request.getCategoriaId(),
            CreateSubcategoriaRequest.from(request.toEntity())
        );
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(SubcategoriaResponse.from(subcategoria));
    }

    @PutMapping("/subcategorias")
    public ResponseEntity<SubcategoriaResponse> updateSubcategoria(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody ChangeSubcategoriaRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        Subcategoria subcategoria = subcategoriaService.update(
            autenticado.getNegocio(),
            request.getIdSubcategoria(),
            UpdateSubcategoriaRequest.from(request.toEntity())
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SubcategoriaResponse.from(subcategoria));
    }

    @DeleteMapping("/subcategorias")
    public ResponseEntity<Void> deleteSubcategoria(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteSubcategoriaRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        subcategoriaService.deactivate(autenticado.getNegocio(), request.getIdSubcategoria());
        return ResponseEntity.ok().build();
    }

}