package com.stockeate.api.parametros.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.repositories.ParametrosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParametrosService {
    
    private final ParametrosRepository parametrosRepository;

    public Parametros getById (Long id) {
        return parametrosRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Parametros no encontrados con id: " + id));
    }

    @Transactional
    public Parametros createDefault () {
        Parametros parametros = Parametros.builder()
            .unaCompraDistintosProveedores(true)
            .exigirFormaDePago(false)
            .build();

        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros unaCompraDistintosProveedores(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setUnaCompraDistintosProveedores(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros exigirFormaDePago(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setExigirFormaDePago(activo);
        return parametrosRepository.save(parametros);
    }

}
