package com.stockeate.api.dominio.productos.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.categoria.entities.Subcategoria;
import com.stockeate.api.dominio.categoria.services.CategoriaService;
import com.stockeate.api.dominio.categoria.services.SubcategoriaService;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.precios.services.PrecioService;
import com.stockeate.api.dominio.productos.dtos.ProductoResponse;
import com.stockeate.api.dominio.productos.dtos.service.CreateProductoRequest;
import com.stockeate.api.dominio.productos.dtos.service.UpdateProductoRequest;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.repositories.ProductoRepository;
import com.stockeate.api.dominio.proveedores.services.ProveedorService;
import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.service.ParametrosService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;
    private final ProveedorService proveedorService;
    private final PrecioService precioService;
    private final ParametrosService parametrosService;
    
    public Producto findById (Negocio negocio, Long id) {
        return productoRepository.findByNegocioAndId(negocio, id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Page<ProductoResponse> getAll (Negocio negocio, Long categoriaId, Long subcategoriaId, Long proveedorId, String busqueda, Pageable pageable) {
        String termino = (busqueda != null && !busqueda.isBlank()) ? "%" + busqueda.toLowerCase() + "%" : null;
        boolean soloSinCategoria = Long.valueOf(0L).equals(categoriaId);
        Long catId = soloSinCategoria ? null : categoriaId;

        if (subcategoriaId != null && catId != null) {
            Subcategoria sub = subcategoriaService.findById(negocio, subcategoriaId);
            if (!sub.getCategoria().getId().equals(catId))
                throw new BusinessException("La subcategoria no pertenece a la categoria indicada");
        }

        return productoRepository.buscar(negocio, catId, soloSinCategoria, subcategoriaId, proveedorId, termino, pageable)
            .map(p -> ProductoResponse.from(p, precioService.precioActual(p, TipoPrecio.VENTA)));
    }

    @Transactional
    public ProductoResponse create (Negocio negocio, CreateProductoRequest request, Long categoriaId, Long subcategoriaId, Long proveedorId, BigDecimal precio) {
        if (productoRepository.existsByNegocioAndDescripcion(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un producto con la descripcion " + request.getDescripcion());
        }

        Producto producto = request.toEntity();

        producto.setNegocio(negocio);
        producto.setActivo(true);
        producto.setStock(request.getStock() != null ? request.getStock() : 0);
        producto.setFechaCreacion(LocalDateTime.now());

        if (categoriaId != null)
            producto.setCategoria(categoriaService.findById(negocio, categoriaId));

        if (subcategoriaId != null) {
            Subcategoria subcategoria = subcategoriaService.findById(negocio, subcategoriaId);
            if (categoriaId == null || !subcategoria.getCategoria().getId().equals(categoriaId))
                throw new com.stockeate.api.exceptions.exceptions.BusinessException("La subcategoria no pertenece a la categoria indicada");
            producto.setSubcategoria(subcategoria);
        }

        if (proveedorId != null)
            producto.setProveedor(proveedorService.findById(negocio, proveedorId));

        productoRepository.save(producto);

        precioService.asignarPrecio(producto, precio, TipoPrecio.VENTA);

        return ProductoResponse.from(producto, precioService.precioActual(producto, TipoPrecio.VENTA));
    }

    @Transactional
    public ProductoResponse update (Negocio negocio, Long id, UpdateProductoRequest request, Long categoriaId, Long subcategoriaId, Long proveedorId, BigDecimal precio) {
        Producto producto = findById(negocio, id);

        if (!producto.getDescripcion().equals(request.getDescripcion()) && productoRepository.existsByNegocioAndDescripcion(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un producto con la descripcion " + request.getDescripcion());
        }

        producto = request.update(producto);

        if (categoriaId != null)
            producto.setCategoria(categoriaService.findById(negocio, categoriaId));

        if (subcategoriaId != null) {
            Subcategoria subcategoria = subcategoriaService.findById(negocio, subcategoriaId);
            if (categoriaId == null || !subcategoria.getCategoria().getId().equals(categoriaId))
                throw new com.stockeate.api.exceptions.exceptions.BusinessException("La subcategoria no pertenece a la categoria indicada");
            producto.setSubcategoria(subcategoria);
        }

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
        productoRepository.delete(producto);
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
        Parametros parametros = parametrosService.getById(negocio.getId());

        if (!parametros.getPermitirVenderSinStock() && producto.getStock() < cantidad)
            throw new BusinessException("Stock insuficiente");

        producto.setStock(Math.max(0, producto.getStock() - cantidad));
        productoRepository.save(producto);
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
