package com.stockeate.api.dominio.categoria.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.negocios.entities.Negocio;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>  {
    
    Page<Categoria> findByNegocio(Negocio negocio, Pageable pageable);

    Optional<Categoria> findByNegocioAndId(Negocio negocio, Long id);

    Boolean existsByNegocioAndDescripcion(Negocio negocio, String descripcion);

}
