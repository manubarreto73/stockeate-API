package com.stockeate.api.dominio.productos.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.productos.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Page<Producto> findByActivoTrueAndNegocio(Negocio negocio, Pageable pageable);

    Page<Producto> findByActivoTrueAndNegocioAndCategoria(Negocio negocio, Categoria categoria, Pageable pageable);

    Optional<Producto> findByIdAndNegocioAndActivoTrue(Negocio negocio, Long id);

    Boolean existsByDescripcionAndNegocioAndActivoTrue(Negocio negocio, String descripcion);

    @Modifying
    @Query("UPDATE Producto p SET p.categoria = null WHERE p.categoria.id = :categoriaId")
    void clearCategoria(@Param("categoriaId") Long categoriaId);

}
