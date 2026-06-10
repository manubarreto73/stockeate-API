package com.stockeate.api.dominio.categoria.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;
import com.stockeate.api.dominio.negocios.entities.Negocio;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {

    Optional<Subcategoria> findByIdAndCategoriaNegocio(Long id, Negocio negocio);

    Boolean existsByCategoriaIdAndDescripcion(Long categoriaId, String descripcion);

    List<Subcategoria> findByCategoriaId(Long categoriaId);

    List<Subcategoria> findByCategoriaNegocio(Negocio negocio);

    List<Subcategoria> findByCategoriaIdAndCategoriaNegocio(Long categoriaId, Negocio negocio);

}
