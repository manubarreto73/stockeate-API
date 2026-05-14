package com.stockeate.api.dominio.categoria.services;

import com.stockeate.api.dominio.productos.repositories.ProductoRepository;
import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.categoria.dtos.service.CreateCategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.UpdateCategoriaRequest;
import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.repositories.CategoriaRepository;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public Categoria findById (Negocio negocio, Long id) {
        return categoriaRepository.findByNegocioAndIdAndActivoTrue(negocio, id)
            .orElseThrow(() -> new BusinessException("Categoria no encontrada con id " + id));
    }

    public Page<Categoria> getByNegocio (Negocio negocio, Pageable pageable) {
        return categoriaRepository.findByNegocioAndActivoTrue(negocio, pageable);
    }

    @Transactional
    public Categoria create (Negocio negocio, CreateCategoriaRequest request) {
        if (categoriaRepository.existsByNegocioAndDescripcionAndActivoTrue(negocio, request.getDescripcion()))
            throw new BusinessException("Ya existe una categoria con la descripcion " + request.getDescripcion());

        Categoria categoria = request.toEntity();

        categoria.setNegocio(negocio);
        categoria.setFechaCreacion(LocalDateTime.now());
        categoria.setActivo(true);

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update (Negocio negocio, Long id, UpdateCategoriaRequest request) {
        Categoria categoria = findById(negocio, id);

        if (!categoria.getDescripcion().equals(request.getDescripcion()) && categoriaRepository.existsByNegocioAndDescripcionAndActivoTrue(negocio, request.getDescripcion()))
            throw new BusinessException("Ya existe una categoria con la descripcion " + request.getDescripcion());

        if (!request.hasChanges(categoria))
            throw new BusinessException("La entidad enviada para actualizar no contiene cambios");

        return categoriaRepository.save(request.update(categoria));
    }

    @Transactional
    public void deactivate (Negocio negocio, Long id) {
        Categoria categoria = findById(negocio, id);
        categoria.setActivo(false);
        productoRepository.clearCategoria(id);
        categoriaRepository.save(categoria);
    } 

}
