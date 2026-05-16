package com.stockeate.api.dominio.ventas.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.ventas.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    Page<Venta> findByNegocio(Negocio negocio, Pageable pageable);

    Optional<Venta> findByNegocioAndId(Negocio negocio, Long id);

}
