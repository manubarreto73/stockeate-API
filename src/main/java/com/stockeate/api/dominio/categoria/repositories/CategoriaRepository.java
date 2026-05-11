package com.stockeate.api.dominio.categoria.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.negocios.entities.Negocio;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>  {
    
    Page<Categoria> findByNegocioAndActivoTrue(Negocio negocio, Pageable pageable);

    Optional<Categoria> findByIdAndNegocioAndActivoTrue(Negocio negocio, Long id);

    Boolean existsByDescripcionAndNegocioAndActivoTrue(Negocio negocio, String descripcion);

}
