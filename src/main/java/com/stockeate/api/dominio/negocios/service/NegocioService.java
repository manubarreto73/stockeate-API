package com.stockeate.api.dominio.negocios.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.negocios.dtos.service.CreateNegocioRequest;
import com.stockeate.api.dominio.negocios.dtos.service.UpdateNegocioRequest;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.negocios.repository.NegocioRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.service.ParametrosService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NegocioService {
    
    private final NegocioRepository negocioRepository;
    private final ParametrosService parametrosService;

    public Page<Negocio> getAll(Pageable pageable) {
        return negocioRepository.findByActivoTrue(pageable);
    }

    public Negocio findById (Long id) {
        return negocioRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Negocio no encontrado con id " + id));
    }

    @Transactional
    public Negocio create (CreateNegocioRequest request) {
        if (negocioRepository.existsByNombreNegocio(request.getNombreNegocio()))
            throw new BusinessException("Ya existe un negocio con ese nombre");

        Negocio negocio = request.toEntity();

        Parametros parametros = parametrosService.createDefault();

        negocio.setParametros(parametros);        
        negocio.setFechaCreacion(LocalDateTime.now());
        negocio.setActivo(true);

        return negocioRepository.save(negocio);
    }

    @Transactional
    public Negocio update (Long id, UpdateNegocioRequest request) {
        Negocio negocio = findById(id);

        if (!negocio.getNombreNegocio().equals(request.getNombreNegocio()) && negocioRepository.existsByNombreNegocio(request.getNombreNegocio()))
            throw new BusinessException("Ya existe un negocio con ese nombre");

        if (!request.hasChanges(negocio))
            throw new BusinessException("La entidad enviada para actualizar no contiene cambios");
        return negocioRepository.save(request.update(negocio));
    }

}
