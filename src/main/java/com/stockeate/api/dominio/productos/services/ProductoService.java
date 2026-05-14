package com.stockeate.api.dominio.productos.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.services.CategoriaService;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.precios.services.PrecioService;
import com.stockeate.api.dominio.productos.dtos.ProductoResponse;
import com.stockeate.api.dominio.productos.dtos.service.CreateProductoRequest;
import com.stockeate.api.dominio.productos.dtos.service.UpdateProductoRequest;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.repositories.ProductoRepository;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;
import com.stockeate.api.dominio.proveedores.services.ProveedorService;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;
    private final PrecioService precioService;
    
    public Producto findById (Negocio negocio, Long id) {
        return productoRepository.findByNegocioAndIdAndActivoTrue(negocio, id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Page<ProductoResponse> getAll (Negocio negocio, Pageable pageable) {
        Page<Producto> productos = productoRepository.findByActivoTrueAndNegocio(negocio, pageable);
        return productos.map(producto -> ProductoResponse.from(producto, precioService.precioActual(producto, TipoPrecio.VENTA)));
    }

    public Page<ProductoResponse> getByCategoria (Negocio negocio, Long categoriaId, Pageable pageable) {
        Categoria categoria = categoriaService.findById(negocio, categoriaId);
        Page<Producto> productos = productoRepository.findByActivoTrueAndNegocioAndCategoria(negocio, categoria, pageable);
        return productos.map(producto -> ProductoResponse.from(producto, precioService.precioActual(producto, TipoPrecio.VENTA)));
    }

    @Transactional
    public ProductoResponse create (Negocio negocio, CreateProductoRequest request, Long categoriaId, Long proveedorId, BigDecimal precio) {
        if (productoRepository.existsByNegocioAndDescripcionAndActivoTrue(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un producto con la descripcion " + request.getDescripcion());
        }
        
        Producto producto = request.toEntity();

        producto.setNegocio(negocio);
        producto.setActivo(true);
        producto.setStock(request.getStock() != null ? request.getStock() : 0);
        producto.setFechaCreacion(LocalDateTime.now());

        if (categoriaId != null)
            producto.setCategoria(categoriaService.findById(negocio, categoriaId));

        if (proveedorId != null)
            producto.setProveedor(proveedorService.findById(negocio, proveedorId));

        productoRepository.save(producto);

        precioService.asignarPrecio(producto, precio, TipoPrecio.VENTA);

        return ProductoResponse.from(producto, precioService.precioActual(producto, TipoPrecio.VENTA));
    }

    @Transactional
    public ProductoResponse update (Negocio negocio, Long id, UpdateProductoRequest request, Long categoriaId, Long proveedorId, BigDecimal precio) {
        Producto producto = findById(negocio, id);

        if (!producto.getDescripcion().equals(request.getDescripcion()) && productoRepository.existsByNegocioAndDescripcionAndActivoTrue(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un producto con la descripcion " + request.getDescripcion());
        }

        producto = request.update(producto);

        if (categoriaId != null)
            producto.setCategoria(categoriaService.findById(negocio, categoriaId));

        if (proveedorId != null)
            producto.setProveedor(proveedorService.findById(negocio, proveedorId));

        productoRepository.save(producto);

        if (precio != null && precio != precioService.precioActual(producto, TipoPrecio.VENTA).getMonto())
            precioService.asignarPrecio(producto, precio, TipoPrecio.VENTA);

        return ProductoResponse.from(producto, precioService.precioActual(producto, TipoPrecio.VENTA));
    }

    @Transactional
    public void deactivate (Negocio negocio, Long id) {
        Producto producto = findById(negocio, id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Transactional
    public void aumentarStock (Negocio negocio, Long id, Integer cantidad) {
        Producto producto = findById(negocio, id);

        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);
    }

    @Transactional
    public void reducirStock (Negocio negocio, Long id, Integer cantidad) {
        Producto producto = findById(negocio, id);

        if (producto.getStock() < cantidad)
            throw new BusinessException("Stock insuficiente");

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    @Transactional
    public ProductoResponse asignarCategoria (Negocio negocio, Long id, Long idCategoria) {
        Categoria categoria = categoriaService.findById(negocio, idCategoria);
        Producto producto = findById(negocio, id);
        producto.setCategoria(categoria);
        return ProductoResponse.from(productoRepository.save(producto), precioService.precioActual(producto, TipoPrecio.VENTA));
    }

    @Transactional
    public ProductoResponse asignarProveedor (Negocio negocio, Long id, Long idProveedor) {
        Proveedor proveedor = proveedorService.findById(negocio, idProveedor);
        Producto producto = findById(negocio, id);
        producto.setProveedor(proveedor);
        return ProductoResponse.from(productoRepository.save(producto), precioService.precioActual(producto, TipoPrecio.VENTA));
    }

    @Transactional
    public void removerCategoria (Long categoriaId) {
        productoRepository.clearCategoria(categoriaId);
    }

    @Transactional
    public void removerProveedor (Long proveedorId) {
        productoRepository.clearProveedor(proveedorId);
    }

}
