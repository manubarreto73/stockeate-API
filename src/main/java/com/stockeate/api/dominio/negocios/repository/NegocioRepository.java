package com.stockeate.api.dominio.negocios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockeate.api.dominio.negocios.entities.Negocio;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long>{

    Boolean existsByNombreNegocio(String nombreNegocio);

}
