package com.stockeate.api.dominio.clientes.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.clientes.dtos.command.CreateClienteRequest;
import com.stockeate.api.dominio.clientes.dtos.command.UpdateClienteCommand;
import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.clientes.repositories.ClienteRepository;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

    public Cliente findById (Negocio negocio, Long id) {
        return clienteRepository.findByNegocioAndId(negocio, id)
            .orElseThrow(() -> new BusinessException("Cliente no encontrado con id " + id));
    }

    public Page<Cliente> getByNegocio (Negocio negocio, Pageable pageable) {
        return clienteRepository.findByNegocio(negocio, pageable);
    }

    @Transactional
    public Cliente create (Negocio negocio, CreateClienteRequest request) {
        if (clienteRepository.existsByNegocioAndNombreCompleto(negocio, request.getNombreCompleto()))
            throw new BusinessException("Ya existe un cliente con el nombre " + request.getNombreCompleto());

        Cliente cliente = request.toEntity();

        cliente.setNegocio(negocio);
        cliente.setFechaCreacion(LocalDateTime.now());
        cliente.setActivo(true);

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update (Negocio negocio, Long id, UpdateClienteCommand request) {
        Cliente cliente = findById(negocio, id);

        if (!cliente.getNombreCompleto().equals(request.getNombreCompleto()) && clienteRepository.existsByNegocioAndNombreCompleto(negocio, request.getNombreCompleto()))
            throw new BusinessException("Ya existe un cliente con el nombre " + request.getNombreCompleto());

        return clienteRepository.save(request.update(cliente));
    }

    @Transactional
    public void deactivate (Negocio negocio, Long id) {
        Cliente cliente = findById(negocio, id);
        clienteRepository.delete(cliente);
    }

}
