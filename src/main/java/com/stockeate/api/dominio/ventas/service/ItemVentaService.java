package com.stockeate.api.dominio.ventas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.precios.services.PrecioService;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.ventas.entities.ItemVenta;
import com.stockeate.api.dominio.ventas.entities.Venta;
import com.stockeate.api.dominio.ventas.repositories.ItemVentaRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemVentaService {
    
    private final ItemVentaRepository itemVentaRepository;
    private final PrecioService precioService;

    public ItemVenta findById (Long id) {
        return itemVentaRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Item de venta no encontrado con id " + id));
    }

    @Transactional
    public ItemVenta addItem(Venta venta, Producto producto, Integer cantidad) {
        if (itemVentaRepository.existsByVentaAndProducto(venta, producto))
            throw new BusinessException("Ya existe ese producto cargado en otro item de la venta");

        if (cantidad <= 0)
            throw new BusinessException("La cantidad debe ser mayor a 0");

        Precio precio = precioService.precioActual(producto, TipoPrecio.VENTA);

        ItemVenta item = ItemVenta.builder()
            .venta(venta)
            .producto(producto)
            .precio(precio)
            .cantidad(cantidad)
            .build();

        return itemVentaRepository.save(item);
    }

    @Transactional
    public ItemVenta editItem(Long id, Integer cantidad) {
        if (cantidad <= 0)
            throw new BusinessException("La cantidad debe ser mayor a 0");

        ItemVenta item = findById(id);
        item.setCantidad(cantidad);
        return itemVentaRepository.save(item);
    }

    @Transactional
    public void removeItem (Long id) {
        ItemVenta item = findById(id);
        itemVentaRepository.delete(item);
    }

}
