package com.stockeate.api.dominio.productos.controller;

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

import com.stockeate.api.dominio.productos.dtos.ProductoResponse;
import com.stockeate.api.dominio.productos.dtos.controller.AssignCategoriaRequest;
import com.stockeate.api.dominio.productos.dtos.controller.AssignProveedorRequest;
import com.stockeate.api.dominio.productos.dtos.controller.ChangeProductoRequest;
import com.stockeate.api.dominio.productos.dtos.controller.DeleteProductoRequest;
import com.stockeate.api.dominio.productos.dtos.controller.RegisterProductoRequest;
import com.stockeate.api.dominio.productos.dtos.service.CreateProductoRequest;
import com.stockeate.api.dominio.productos.dtos.service.UpdateProductoRequest;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.services.ProductoService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.ApiConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<Page<ProductoResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, ApiConstants.PAGE_SIZE, sort);
        Page<Producto> productos = productoService.getAll(autenticado.getNegocio(), pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productos.map(ProductoResponse::from));
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<Page<ProductoResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam Long categoriaId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, ApiConstants.PAGE_SIZE, sort);
        Page<Producto> productos = productoService.getByCategoria(autenticado.getNegocio(), categoriaId, pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productos.map(ProductoResponse::from));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody RegisterProductoRequest request
    ) {
        Producto producto = productoService.create(
            autenticado.getNegocio(), 
            CreateProductoRequest.from(request.toEntity()), 
            request.getCategoriaId(), 
            request.getProveedorId()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProductoResponse.from(producto));
    }

    @PutMapping
    public ResponseEntity<ProductoResponse> update(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody ChangeProductoRequest request
    ) {
        Producto producto = productoService.update(
            autenticado.getNegocio(),
            request.getId(),
            UpdateProductoRequest.from(request.toEntity()), 
            request.getCategoriaId(), 
            request.getProveedorId()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProductoResponse.from(producto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteProductoRequest request
    ) {
        productoService.deactivate(autenticado.getNegocio(), request.getId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

    @PutMapping("/categoria")
    public ResponseEntity<ProductoResponse> assignCategoria(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody AssignCategoriaRequest request
    ) {
        Producto producto = productoService.asignarCategoria(autenticado.getNegocio(), request.getProductoId(), request.getCategoriaId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProductoResponse.from(producto));
    }

    @PutMapping("/proveedor")
    public ResponseEntity<ProductoResponse> assignProveedor(
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody AssignProveedorRequest request
    ) {
        Producto producto = productoService.asignarProveedor(autenticado.getNegocio(), request.getProductoId(), request.getProveedorId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProductoResponse.from(producto));
    }

}
