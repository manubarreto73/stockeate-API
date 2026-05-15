package com.stockeate.api.dominio.compras.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.compras.dtos.commands.CreateCompraCommand;
import com.stockeate.api.dominio.compras.dtos.request.RegisterItemRequest;
import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;
import com.stockeate.api.dominio.compras.repositories.CompraRepository;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.services.ProductoService;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompraService {
    
    private final CompraRepository compraRepository;
    private final ItemCompraService itemCompraService;
    private final ProductoService productoService;

    public Compra findById (Negocio negocio, Long id) {
        return compraRepository.findByNegocioAndId(negocio, id)
            .orElseThrow(() -> new BusinessException("Compra no encontrada con id " + id));
    }

    public Page<Compra> getAll (Negocio negocio, Pageable pageable) {
        return compraRepository.findByNegocio(negocio, pageable);
    }

    @Transactional
    public Compra create (CreateCompraCommand request) {
        Compra compra = request.toEntity();

        compra.setFechaCreacion(LocalDateTime.now());
        compra.setRecibida(false);
        
        compraRepository.save(compra);

        List<ItemCompra> items = new ArrayList<ItemCompra>();

        for (RegisterItemRequest item : request.getItems()) {
            Producto producto = productoService.findById(request.getNegocio(), item.getProductoId());
            ItemCompra itemNuevo = itemCompraService.addItem(compra, producto, item.getCantidad());

            items.add(itemNuevo);
        }

        compra.setItems(items);

        return compra;
    }

    @Transactional 
    public Compra marcarComoRecibida (Negocio negocio, Long id) {
        Compra compra = findById(negocio, id);

        if (compra.getRecibida())
            throw new BusinessException("La compra ya fue recibida");

        for (ItemCompra item : compra.getItems()) {
            productoService.reducirStock(negocio, item.getProducto().getId(), item.getCantidad());
        }

        compra.setRecibida(true);
        compra.setFechaRecepcion(LocalDateTime.now());
        return compraRepository.save(compra);
    }

    @Transactional
    public void delete (Negocio negocio, Long id) {
        Compra compra = findById(negocio, id);
        compraRepository.delete(compra);
    } 

}
