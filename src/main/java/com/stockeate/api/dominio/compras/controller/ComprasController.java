package com.stockeate.api.dominio.compras.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockeate.api.dominio.compras.dtos.CompraResponse;
import com.stockeate.api.dominio.compras.dtos.ItemCompraResponse;
import com.stockeate.api.dominio.compras.dtos.commands.CreateCompraCommand;
import com.stockeate.api.dominio.compras.dtos.request.AddItemRequest;
import com.stockeate.api.dominio.compras.dtos.request.DeleteCompraRequest;
import com.stockeate.api.dominio.compras.dtos.request.EditItemRequest;
import com.stockeate.api.dominio.compras.dtos.request.MarcarComoRecibidaRequest;
import com.stockeate.api.dominio.compras.dtos.request.RegisterCompraRequest;
import com.stockeate.api.dominio.compras.dtos.request.RemoveItemRequest;
import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;
import com.stockeate.api.dominio.compras.services.CompraService;
import com.stockeate.api.dominio.compras.services.ItemCompraService;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.services.ProductoService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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


@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class ComprasController {
    
    private final CompraService compraService;
    private final ItemCompraService itemCompraService;
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<Page<CompraResponse>> getAll (
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, Constantes.PAGE_SIZE, sort);
        Page<Compra> compras = compraService.getAll(autenticado.getNegocio(), pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(compras.map(CompraResponse::from));
    }

    @PostMapping
    public ResponseEntity<CompraResponse> create (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterCompraRequest request
    ) {
        Compra compra = compraService.create(new CreateCompraCommand(autenticado.getNegocio(), autenticado, request.getItems()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CompraResponse.from(compra));
    }

    @PutMapping("/recibida")
    public ResponseEntity<CompraResponse> marcarComoRecibida (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody MarcarComoRecibidaRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CompraResponse.from(compraService.marcarComoRecibida(autenticado.getNegocio(), request.getId())));
    }

    @PostMapping("/items")
    public ResponseEntity<ItemCompraResponse> addItem (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody AddItemRequest request
    ) {
        Compra compra = compraService.findById(autenticado.getNegocio(), request.getCompraId());
        Producto producto = productoService.findById(autenticado.getNegocio(), request.getProductoId());
        ItemCompra item = itemCompraService.addItem(compra, producto, request.getCantidad());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ItemCompraResponse.from(item, compra));
    }

    @PutMapping("/items")
    public ResponseEntity<ItemCompraResponse> editItem (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody EditItemRequest request
    ) {
        ItemCompra item = itemCompraService.editItem(request.getItemCompraId(), request.getCantidad());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ItemCompraResponse.from(item, item.getCompra()));
    }

    @DeleteMapping("/items")
    public ResponseEntity<Void> removeItem (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RemoveItemRequest request
    ) {
        itemCompraService.removeItem(request.getItemCompraId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody DeleteCompraRequest request
    ) {
        compraService.delete(autenticado.getNegocio(), request.getCompraId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

}
