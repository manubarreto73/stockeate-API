package com.stockeate.api.dominio.proveedores.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.proveedores.dtos.service.CreateProveedorRequest;
import com.stockeate.api.dominio.proveedores.dtos.service.UpdateProveedorRequest;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;
import com.stockeate.api.dominio.proveedores.repositories.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    
    public Proveedor findById (Negocio negocio, Long id) {
        return proveedorRepository.findByNegocioAndId(negocio, id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
    }

    public Page<Proveedor> getAll (Negocio negocio, Pageable pageable) {
        return proveedorRepository.findByNegocio(negocio, pageable);
    }

    @Transactional
    public Proveedor create (Negocio negocio, CreateProveedorRequest request) {
        if (proveedorRepository.existsByNegocioAndDescripcion(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un proveedor con la descripcion " + request.getDescripcion());
        }

        Proveedor proveedor = request.toEntity();

        proveedor.setNegocio(negocio);
        proveedor.setActivo(true);

        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public Proveedor update (Negocio negocio, Long id, UpdateProveedorRequest request) {
        Proveedor proveedor = findById(negocio, id);

        if (!proveedor.getDescripcion().equals(request.getDescripcion()) && proveedorRepository.existsByNegocioAndDescripcion(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un proveedor con la descripcion " + request.getDescripcion());
        }

        proveedor = request.update(proveedor);

        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public void deactivate (Negocio negocio, Long id) {
        Proveedor proveedor = findById(negocio, id);
        proveedorRepository.delete(proveedor);
    }

}