package com.stockeate.api.dominio.categoria.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.categoria.dtos.service.CreateSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.dtos.service.UpdateSubcategoriaRequest;
import com.stockeate.api.dominio.categoria.entities.Subcategoria;
import com.stockeate.api.dominio.categoria.repositories.CategoriaRepository;
import com.stockeate.api.dominio.categoria.repositories.SubcategoriaRepository;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.productos.repositories.ProductoRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public List<Subcategoria> getByNegocio(Negocio negocio, Long categoriaId) {
        if (categoriaId != null)
            return subcategoriaRepository.findByCategoriaIdAndCategoriaNegocio(categoriaId, negocio);
        return subcategoriaRepository.findByCategoriaNegocio(negocio);
    }

    public Subcategoria findById(Negocio negocio, Long id) {
        return subcategoriaRepository.findByIdAndCategoriaNegocio(id, negocio)
            .orElseThrow(() -> new BusinessException("Subcategoria no encontrada con id " + id));
    }

    @Transactional
    public Subcategoria create(Negocio negocio, Long categoriaId, CreateSubcategoriaRequest request) {
        var categoria = categoriaRepository.findByNegocioAndId(negocio, categoriaId)
            .orElseThrow(() -> new BusinessException("Categoria no encontrada con id " + categoriaId));

        if (subcategoriaRepository.existsByCategoriaIdAndDescripcion(categoriaId, request.getDescripcion()))
            throw new BusinessException("Ya existe una subcategoria con la descripcion " + request.getDescripcion());

        Subcategoria subcategoria = request.toEntity();
        subcategoria.setCategoria(categoria);
        subcategoria.setFechaCreacion(LocalDateTime.now());
        subcategoria.setActivo(true);

        return subcategoriaRepository.save(subcategoria);
    }

    @Transactional
    public Subcategoria update(Negocio negocio, Long id, UpdateSubcategoriaRequest request) {
        Subcategoria subcategoria = findById(negocio, id);

        if (!subcategoria.getDescripcion().equals(request.getDescripcion()) &&
            subcategoriaRepository.existsByCategoriaIdAndDescripcion(subcategoria.getCategoria().getId(), request.getDescripcion()))
            throw new BusinessException("Ya existe una subcategoria con la descripcion " + request.getDescripcion());

        return subcategoriaRepository.save(request.update(subcategoria));
    }

    @Transactional
    public void deactivate(Negocio negocio, Long id) {
        Subcategoria subcategoria = findById(negocio, id);
        productoRepository.clearSubcategoria(id);
        subcategoriaRepository.delete(subcategoria);
    }

    @Transactional
    public void deactivateByCategoria(Long categoriaId) {
        List<Subcategoria> subcategorias = subcategoriaRepository.findByCategoriaId(categoriaId);
        subcategoriaRepository.deleteAll(subcategorias);
    }

}
