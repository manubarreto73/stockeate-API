package com.stockeate.api.dominio.ventas.controllers;

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

import com.stockeate.api.dominio.compras.dtos.request.AddItemRequest;
import com.stockeate.api.dominio.compras.dtos.request.DeleteCompraRequest;
import com.stockeate.api.dominio.compras.dtos.request.EditItemRequest;
import com.stockeate.api.dominio.compras.dtos.request.RemoveItemRequest;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.services.ProductoService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.ventas.dtos.ItemVentaResponse;
import com.stockeate.api.dominio.ventas.dtos.VentaResponse;
import com.stockeate.api.dominio.ventas.dtos.command.CreateVentaCommand;
import com.stockeate.api.dominio.ventas.dtos.request.RegisterVentaRequest;
import com.stockeate.api.dominio.ventas.entities.ItemVenta;
import com.stockeate.api.dominio.ventas.entities.Venta;
import com.stockeate.api.dominio.ventas.service.ItemVentaService;
import com.stockeate.api.dominio.ventas.service.VentasService;
import com.stockeate.api.parametros.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {
    
    private final VentasService ventasService;
    private final ProductoService productoService;
    private final ItemVentaService itemVentaService;

    @GetMapping
    public ResponseEntity<Page<VentaResponse>> getAll (
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, Constantes.PAGE_SIZE, sort);
        Page<Venta> ventas = ventasService.getAll(autenticado.getNegocio(), pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ventas.map(VentaResponse::from));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> create (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterVentaRequest request
    ) {
        Venta venta = ventasService.create(new CreateVentaCommand(autenticado.getNegocio(), autenticado, request.getClienteid(), request.getFormaDePagoId(), request.getItems()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(VentaResponse.from(venta));
    }

    @PostMapping("/items")
    public ResponseEntity<ItemVentaResponse> addItem (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody AddItemRequest request
    ) {
        Venta venta = ventasService.findById(autenticado.getNegocio(), request.getCompraId());
        Producto producto = productoService.findById(autenticado.getNegocio(), request.getProductoId());
        ItemVenta item = itemVentaService.addItem(venta, producto, request.getCantidad());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ItemVentaResponse.from(item, venta));
    }

    @PutMapping("/items")
    public ResponseEntity<ItemVentaResponse> editItem (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody EditItemRequest request
    ) {
        ItemVenta item = itemVentaService.editItem(request.getItemCompraId(), request.getCantidad());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ItemVentaResponse.from(item, item.getVenta()));
    }

    @DeleteMapping("/items")
    public ResponseEntity<Void> removeItem (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RemoveItemRequest request
    ) {
        itemVentaService.removeItem(request.getItemCompraId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody DeleteCompraRequest request
    ) {
        ventasService.delete(autenticado.getNegocio(), request.getCompraId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null);
    }

}
