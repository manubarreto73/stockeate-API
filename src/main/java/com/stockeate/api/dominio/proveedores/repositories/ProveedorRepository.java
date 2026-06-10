package com.stockeate.api.dominio.proveedores.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>  {
    
    Page<Proveedor> findByNegocio(Negocio negocio, Pageable pageable);

    Optional<Proveedor> findByNegocioAndId(Negocio negocio, Long id);

    Boolean existsByNegocioAndDescripcion(Negocio negocio, String descripcion);

}
