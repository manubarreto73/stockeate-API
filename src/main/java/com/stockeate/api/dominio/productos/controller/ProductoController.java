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
import com.stockeate.api.dominio.productos.dtos.controller.ChangeProductoRequest;
import com.stockeate.api.dominio.productos.dtos.controller.DeleteProductoRequest;
import com.stockeate.api.dominio.productos.dtos.controller.RegisterProductoRequest;
import com.stockeate.api.dominio.productos.dtos.service.CreateProductoRequest;
import com.stockeate.api.dominio.productos.dtos.service.UpdateProductoRequest;
import com.stockeate.api.dominio.productos.services.ProductoService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.Constantes;
import com.stockeate.api.parametros.service.PermisosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final PermisosService permisosService;

    @GetMapping
    public ResponseEntity<Page<ProductoResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(required = false) Long categoriaId,
        @RequestParam(required = false) Long subcategoriaId,
        @RequestParam(required = false) Long proveedorId,
        @RequestParam(required = false) String busqueda,
        @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, Constantes.PAGE_SIZE, Sort.by("descripcion").ascending());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productoService.getAll(autenticado.getNegocio(), categoriaId, subcategoriaId, proveedorId, busqueda, pageable));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody RegisterProductoRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        ProductoResponse producto = productoService.create(
            autenticado.getNegocio(),
            CreateProductoRequest.from(request.toEntity()),
            request.getCategoriaId(),
            request.getSubcategoriaId(),
            request.getProveedorId(),
            request.getPrecio()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(producto);
    }

    @PutMapping
    public ResponseEntity<ProductoResponse> update(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody ChangeProductoRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        ProductoResponse producto = productoService.update(
            autenticado.getNegocio(),
            request.getIdProducto(),
            UpdateProductoRequest.from(request.toEntity()),
            request.getCategoriaId(),
            request.getSubcategoriaId(),
            request.getProveedorId(),
            request.getPrecio()
        );
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(producto);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteProductoRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        productoService.deactivate(autenticado.getNegocio(), request.getIdProducto());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

}
