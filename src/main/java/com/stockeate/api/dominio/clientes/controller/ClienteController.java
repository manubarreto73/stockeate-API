package com.stockeate.api.dominio.clientes.controller;

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

import com.stockeate.api.dominio.clientes.dtos.ClienteResponse;
import com.stockeate.api.dominio.clientes.dtos.command.CreateClienteRequest;
import com.stockeate.api.dominio.clientes.dtos.command.UpdateClienteCommand;
import com.stockeate.api.dominio.clientes.dtos.request.UpdateClienteRequest;
import com.stockeate.api.dominio.clientes.dtos.request.DeleteClienteRequest;
import com.stockeate.api.dominio.clientes.dtos.request.RegisterClienteRequest;
import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.clientes.service.ClienteService;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.parametros.Constantes;
import com.stockeate.api.parametros.service.PermisosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final PermisosService permisosService;

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> getAll(
        @AuthenticationPrincipal Usuario autenticado,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, Constantes.PAGE_SIZE, sort);
        Page<Cliente> clientes = clienteService.getByNegocio(autenticado.getNegocio(), pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(clientes.map(ClienteResponse::from));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> create (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody RegisterClienteRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        Cliente cliente = clienteService.create(autenticado.getNegocio(), CreateClienteRequest.from(request.toEntity()));
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ClienteResponse.from(cliente));
    }

    @PutMapping
    public ResponseEntity<ClienteResponse> update (
        @AuthenticationPrincipal Usuario autenticado,
        @Valid @RequestBody UpdateClienteRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        Cliente cliente = clienteService.update(autenticado.getNegocio(), request.getIdCliente(), UpdateClienteCommand.from(request.toEntity()));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ClienteResponse.from(cliente));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete (
        @AuthenticationPrincipal Usuario autenticado,
        @RequestBody DeleteClienteRequest request
    ) {
        permisosService.verificarAbm(autenticado);
        clienteService.deactivate(autenticado.getNegocio(), request.getIdCliente());
        return ResponseEntity.ok().build();
    }

}   
