package com.stockeate.api.dominio.compras.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;
import com.stockeate.api.dominio.productos.entities.Producto;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    
    Optional<ItemCompra> findById(Long id);

    Boolean existsByCompraAndProducto(Compra compra, Producto producto);

}