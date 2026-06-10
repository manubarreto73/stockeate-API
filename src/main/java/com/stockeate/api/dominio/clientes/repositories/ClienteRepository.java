package com.stockeate.api.dominio.clientes.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.negocios.entities.Negocio;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Page<Cliente> findByNegocio(Negocio negocio, Pageable pageable);

    Optional<Cliente> findByNegocioAndId(Negocio negocio, Long id);

    Boolean existsByNegocioAndNombreCompleto(Negocio negocio, String nombreCompleto);

}
