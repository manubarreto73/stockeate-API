package com.stockeate.api.dominio.negocios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockeate.api.dominio.negocios.entities.Negocio;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long>{
 
    List<Negocio> getAll();

    List<Negocio> findByActivoTrue();

    Optional<Negocio> findById(Long id);

    Boolean existsByNombreNegocio(String nombreNegocio);

}
