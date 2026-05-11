package com.stockeate.api.dominio.proveedores.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;
import com.stockeate.api.dominio.proveedores.repositories.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    
    public Proveedor findById (Negocio negocio, Long id) {
        return proveedorRepository.findByIdAndActivoTrueAndNegocio(negocio, id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
    }

}