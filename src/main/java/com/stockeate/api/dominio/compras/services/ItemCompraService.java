package com.stockeate.api.dominio.compras.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;
import com.stockeate.api.dominio.compras.repositories.ItemCompraRepository;
import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.precios.services.PrecioService;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCompraService {

    private final ItemCompraRepository itemCompraRepository;
    private final PrecioService precioService;

    public ItemCompra findById (Long id) {
        return itemCompraRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Item de compra no encontrado con id " + id));
    }

    @Transactional
    public ItemCompra addItem(Compra compra, Producto producto, Integer cantidad) {
        if (itemCompraRepository.existsByCompraAndProducto(compra, producto))
            throw new BusinessException("Ya existe ese producto cargado en otro item de la compra");

        Precio precio = precioService.precioActual(producto, TipoPrecio.COMPRA);

        ItemCompra item = ItemCompra.builder()
            .compra(compra)
            .producto(producto)
            .precio(precio)
            .cantidad(cantidad)
            .build();

        return itemCompraRepository.save(item);
    }

    @Transactional
    public ItemCompra editItem(Long id, Integer cantidad) {
        ItemCompra item = findById(id);
        item.setCantidad(cantidad);
        return itemCompraRepository.save(item);
    }

    @Transactional
    public void removeItem (Long id) {
        ItemCompra item = findById(id);
        itemCompraRepository.delete(item);
    }

}
