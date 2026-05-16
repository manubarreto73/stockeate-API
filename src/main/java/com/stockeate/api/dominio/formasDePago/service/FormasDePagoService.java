package com.stockeate.api.dominio.formasDePago.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.formasDePago.entities.FormaDePago;

import com.stockeate.api.dominio.formasDePago.repositories.FormasDepagoRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FormasDePagoService {

    private final FormasDepagoRepository formasDepagoRepository;

    public Page<FormaDePago> getAll (Pageable pageable) {
        return formasDepagoRepository.findAll(pageable);
    }

    public FormaDePago findById (Long id) {
        return formasDepagoRepository.findById(id)
            .orElseThrow(() -> new BusinessException("FormaDePago no encontrada con id " + id));
    }

}