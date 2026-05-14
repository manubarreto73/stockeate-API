package com.stockeate.api.dominio.precios.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.productos.entities.Producto;

public interface PrecioRespository extends JpaRepository<Precio, Long> {
    
    Optional<Precio> findByProductoAndTipoAndHastaIsNull (Producto producto, TipoPrecio tipo);

    @Query("""
        SELECT p FROM Precio p 
        WHERE p.producto = :producto AND 
            p.desde <= :fecha AND 
            (p.hasta IS NULL OR p.hasta > :fecha) AND
            p.tipo = :tipo
    """)
    Optional<Precio> findPrecioPorFecha(
        @Param("producto") Producto producto,
        @Param("fecha") LocalDateTime fecha,
        @Param("tipo") TipoPrecio tipo
    );

}
