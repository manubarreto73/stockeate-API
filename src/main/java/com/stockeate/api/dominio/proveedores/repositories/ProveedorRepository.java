package com.stockeate.api.dominio.proveedores.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>  {
    
    Optional<Proveedor> findByIdAndActivoTrueAndNegocio(Negocio negocio, Long id);

}
