package com.stockeate.api.dominio.ventas.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.clientes.service.ClienteService;
import com.stockeate.api.dominio.formasDePago.entities.FormaDePago;
import com.stockeate.api.dominio.formasDePago.service.FormasDePagoService;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.services.ProductoService;
import com.stockeate.api.dominio.ventas.dtos.command.CreateVentaCommand;
import com.stockeate.api.dominio.ventas.entities.ItemVenta;
import com.stockeate.api.dominio.ventas.entities.Venta;
import com.stockeate.api.dominio.ventas.dtos.request.RegisterItemRequest;
import com.stockeate.api.dominio.ventas.repositories.VentaRepository;
import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.service.ParametrosService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VentasService {
    
    private final VentaRepository ventaRepository;
    private final ClienteService clienteService;
    private final FormasDePagoService formasDePagoService;
    private final ProductoService productoService;
    private final ItemVentaService itemVentaService;
    private final ParametrosService parametrosService;

    public Venta findById (Negocio negocio, Long id) {
        return ventaRepository.findByNegocioAndId(negocio, id)
            .orElseThrow(() -> new BusinessException("Venta no encontrada con id " + id));
    }

    public Page<Venta> getAll (Negocio negocio, Pageable pageable) {
        return ventaRepository.findByNegocio(negocio, pageable);
    }

    @Transactional
    public Venta create (CreateVentaCommand command) {
        Parametros parametros = parametrosService.getById(command.getNegocio().getId());

        Venta venta = command.toEntity();
        FormaDePago forma;
        Cliente cliente;

        if (parametros.getExigirFormaDePagoAlCargarVenta() && command.getFormaDePagoId() == null)
            throw new BusinessException("La forma de pago es obligatoria");
        else
            forma = formasDePagoService.findById(command.getFormaDePagoId());

        if (parametros.getExigirClienteAlCargarVenta() && command.getClienteId() == null)
            throw new BusinessException("El cliente es obligatorio");
        else
            cliente = clienteService.findById(command.getNegocio(), command.getClienteId());
        
        venta.setNegocio(command.getNegocio());
        venta.setVendidoPor(command.getVendidoPor());
        venta.setFechaHora(LocalDateTime.now());
        venta.setCliente(cliente);
        venta.setFormaDePago(forma);

        ventaRepository.save(venta);

        List<ItemVenta> items = new ArrayList<ItemVenta>();

        for (RegisterItemRequest item : command.getItems()) {
            Producto producto = productoService.findById(command.getNegocio(), item.getProductoId());
            ItemVenta itemNuevo = itemVentaService.addItem(venta, producto, item.getCantidad());
            productoService.reducirStock(command.getNegocio(), item.getProductoId(), item.getCantidad());

            items.add(itemNuevo);
        }

        venta.setItems(items);

        return venta;
    }

    @Transactional
    public void delete (Negocio negocio, Long id) {
        Venta venta = findById(negocio, id);

        //Vuelto a sumar el stock
        for (ItemVenta item : venta.getItems()) {
            productoService.aumentarStock(negocio, item.getProducto().getId(), item.getCantidad());
        }

        ventaRepository.delete(venta);
    } 

    //List by cliente

}
