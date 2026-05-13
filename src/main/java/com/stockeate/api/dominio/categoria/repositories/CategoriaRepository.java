package com.stockeate.api.dominio.categoria.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.negocios.entities.Negocio;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>  {
    
    Page<Categoria> findByNegocioAndActivoTrue(Negocio negocio, Pageable pageable);

    Optional<Categoria> findByNegocioAndIdAndActivoTrue(Negocio negocio, Long id);

    Boolean existsByNegocioAndDescripcionAndActivoTrue(Negocio negocio, String descripcion);

}
