package com.stockeate.api.dominio.precios.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.precios.repositories.PrecioRespository;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.exceptions.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrecioService {

    private final PrecioRespository precioRespository;

    public Precio precioActual (Producto producto, TipoPrecio tipo) {
        return precioRespository.findByProductoAndTipoAndHastaIsNull(producto, tipo)
            .orElseThrow(() -> new BusinessException("Precio actual no encontrado del producto con id: " + producto.getId()));
    }

    public Precio precioEnFecha (Producto producto, LocalDateTime fechaHora, TipoPrecio tipo) {
        return precioRespository.findPrecioPorFecha(producto, fechaHora, tipo)
            .orElseThrow(() -> new BusinessException("Precio no encontrado del producto con id: " + producto.getId() + " en la fecha " + fechaHora));
    }
    
    @Transactional
    public Precio asignarPrecio (Producto producto, BigDecimal monto, TipoPrecio tipo) {
        LocalDateTime ahora = LocalDateTime.now();

        Optional<Precio> precioActual = precioRespository.findByProductoAndTipoAndHastaIsNull(producto, tipo);
        if (precioActual.isPresent()) {
            precioActual.get().setHasta(ahora);
            precioRespository.save(precioActual.get());
        }

        Precio precioNuevo = Precio.builder()
            .desde(ahora)
            .monto(monto)
            .producto(producto)
            .build();
        
        return precioRespository.save(precioNuevo);
    }

}
