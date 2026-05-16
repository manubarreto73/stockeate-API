package com.stockeate.api.dominio.ventas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.ventas.entities.ItemVenta;
import com.stockeate.api.dominio.ventas.entities.Venta;

public interface ItemVentaRepository extends JpaRepository<ItemVenta, Long> {
    
    Optional<ItemVenta> findById(Long id);

    Boolean existsByVentaAndProducto(Venta venta, Producto producto);

}
