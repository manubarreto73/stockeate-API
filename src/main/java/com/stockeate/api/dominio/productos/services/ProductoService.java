package com.stockeate.api.dominio.productos.services;

import java.time.LocalDate;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.services.CategoriaService;
import com.stockeate.api.dominio.negocios.entities.Negocio;
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
    
    public Producto findById (Negocio negocio, Long id) {
        return productoRepository.findByIdAndNegocioAndActivoTrue(negocio, id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Page<Producto> getAll (Negocio negocio, Pageable pageable) {
        return productoRepository.findByActivoTrueAndNegocio(negocio, pageable);
    }

    public Page<Producto> getByCategoria (Negocio negocio, Long categoriaId, Pageable pageable) {
        Categoria categoria = categoriaService.findById(negocio, categoriaId);
        return productoRepository.findByActivoTrueAndNegocioAndCategoria(negocio, categoria, pageable);
    }

    @Transactional
    public Producto create (Negocio negocio, CreateProductoRequest request, Long categoriaId, Long proveedorId) {
        if (productoRepository.existsByDescripcionAndNegocioAndActivoTrue(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un producto con la descripcion " + request.getDescripcion());
        }
        
        Producto producto = request.toEntity();

        producto.setNegocio(negocio);
        producto.setActivo(true);
        producto.setStock(request.getStock() != null ? request.getStock() : 0);
        producto.setFechaCreacion(LocalDate.now());

        if (categoriaId != null)
            producto.setCategoria(categoriaService.findById(negocio, categoriaId));

        if (proveedorId != null)
            producto.setProveedor(proveedorService.findById(negocio, proveedorId));

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto update (Negocio negocio, Long id, UpdateProductoRequest request, Long categoriaId, Long proveedorId) {
        if (productoRepository.existsByDescripcionAndNegocioAndActivoTrue(negocio, request.getDescripcion())) {
            throw new RuntimeException("Ya existe un producto con la descripcion " + request.getDescripcion());
        }
        
        Producto producto = findById(negocio, id);

        producto = request.update(producto);

        if (categoriaId != null)
            producto.setCategoria(categoriaService.findById(negocio, categoriaId));

        if (proveedorId != null)
            producto.setProveedor(proveedorService.findById(negocio, proveedorId));

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto deactivate (Negocio negocio, Long id) {
        Producto producto = findById(negocio, id);
        producto.setActivo(false);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto aumentarStock (Negocio negocio, Long id, Integer cantidad) {
        Producto producto = findById(negocio, id);

        producto.setStock(producto.getStock() + cantidad);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto reducirStock (Negocio negocio, Long id, Integer cantidad) {
        Producto producto = findById(negocio, id);

        if (producto.getStock() < cantidad)
            throw new BusinessException("Stock insuficiente");

        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto asignarCategoria (Negocio negocio, Long id, Long idCategoria) {
        Categoria categoria = categoriaService.findById(negocio, idCategoria);
        Producto producto = findById(negocio, id);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto asignarProveedor (Negocio negocio, Long id, Long idProveedor) {
        Proveedor proveedor = proveedorService.findById(negocio, idProveedor);
        Producto producto = findById(negocio, id);
        producto.setProveedor(proveedor);
        return productoRepository.save(producto);
    }

}
