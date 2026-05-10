package com.stockeate.api.dominio.negocios.repository;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockeate.api.dominio.negocios.entities.Negocio;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long>{
 
    Page<Negocio> findAll(Pageable pageable);

    Page<Negocio> findByActivoTrue(Pageable pageable);

    Optional<Negocio> findById(Long id);

    Boolean existsByNombreNegocio(String nombreNegocio);

}
