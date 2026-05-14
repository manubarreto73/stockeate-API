package com.stockeate.api.dominio.compras.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.negocios.entities.Negocio;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    
    Page<Compra> findByNegocio(Negocio negocio, Pageable pageable);

    Optional<Compra> findByNegocioAndId(Negocio negocio, Long id);

}
