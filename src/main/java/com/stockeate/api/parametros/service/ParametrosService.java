package com.stockeate.api.parametros.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.repositories.ParametrosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParametrosService {
    
    private final ParametrosRepository parametrosRepository;

    public Parametros getById (Long id) {
        return parametrosRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Parametros no encontrados con id: " + id));
    }

    @Transactional
    public Parametros createDefault () {
        Parametros parametros = Parametros.builder()
            .unaCompraDistintosProveedores(true)
            .exigirClienteAlCargarVenta(false)
            .exigirFormaDePagoAlCargarVenta(false)
            .permitirVenderSinStock(false)
            .empleadoPuedeCargarCompras(true)
            .empleadoPuedeUtilizarAbm(false)
            .build();

        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros unaCompraDistintosProveedores(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setUnaCompraDistintosProveedores(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros exigirClienteEnVentas(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setExigirClienteAlCargarVenta(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros exigirFormaDePagoEnVentas(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setExigirFormaDePagoAlCargarVenta(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros venderSinStock(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setPermitirVenderSinStock(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros permisoEmpleadoCargarCompras(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setEmpleadoPuedeCargarCompras(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros permisoEmpleadoABM(Long id, Boolean activo) {
        Parametros parametros = getById(id);
        parametros.setEmpleadoPuedeUtilizarAbm(activo);
        return parametrosRepository.save(parametros);
    }

    @Transactional
    public Parametros update(Long id, Parametros data) {
        Parametros parametros = getById(id);
        parametros.setUnaCompraDistintosProveedores(data.getUnaCompraDistintosProveedores());
        parametros.setExigirFormaDePagoAlCargarVenta(data.getExigirFormaDePagoAlCargarVenta());
        parametros.setExigirClienteAlCargarVenta(data.getExigirClienteAlCargarVenta());
        parametros.setPermitirVenderSinStock(data.getPermitirVenderSinStock());
        parametros.setEmpleadoPuedeCargarCompras(data.getEmpleadoPuedeCargarCompras());
        parametros.setEmpleadoPuedeUtilizarAbm(data.getEmpleadoPuedeUtilizarAbm());
        return parametrosRepository.save(parametros);
    }

}
