package com.stockeate.api.dominio.negocios.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.negocios.repository.NegocioRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NegocioService {
    
    private final NegocioRepository negocioRepository;

    public Negocio findById (Long id) {
        return negocioRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Negocio no encontrado con id " + id));
    }

    //crear negocio

    //buscar por id

    //update negocio

}
