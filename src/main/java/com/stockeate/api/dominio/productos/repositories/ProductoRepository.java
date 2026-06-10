package com.stockeate.api.dominio.productos.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.productos.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("""
        SELECT p FROM Producto p
        WHERE p.negocio = :negocio
          AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
          AND (:soloSinCategoria = false OR p.categoria IS NULL)
          AND (:subcategoriaId IS NULL OR p.subcategoria.id = :subcategoriaId)
          AND (:proveedorId IS NULL OR p.proveedor.id = :proveedorId)
          AND (:busqueda IS NULL OR LOWER(p.descripcion) LIKE :busqueda)
        """)
    Page<Producto> buscar(
        @Param("negocio") Negocio negocio,
        @Param("categoriaId") Long categoriaId,
        @Param("soloSinCategoria") boolean soloSinCategoria,
        @Param("subcategoriaId") Long subcategoriaId,
        @Param("proveedorId") Long proveedorId,
        @Param("busqueda") String busqueda,
        Pageable pageable
    );

    Optional<Producto> findByNegocioAndId(Negocio negocio, Long id);

    Boolean existsByNegocioAndDescripcion(Negocio negocio, String descripcion);

    @Modifying
    @Query("UPDATE Producto p SET p.categoria = null, p.subcategoria = null WHERE p.categoria.id = :categoriaId")
    void clearCategoria(@Param("categoriaId") Long categoriaId);

    @Modifying
    @Query("UPDATE Producto p SET p.proveedor = null WHERE p.proveedor.id = :proveedorId")
    void clearProveedor(@Param("proveedorId") Long proveedorId);

    @Modifying
    @Query("UPDATE Producto p SET p.subcategoria = null WHERE p.subcategoria.id = :subcategoriaId")
    void clearSubcategoria(@Param("subcategoriaId") Long subcategoriaId);

}
