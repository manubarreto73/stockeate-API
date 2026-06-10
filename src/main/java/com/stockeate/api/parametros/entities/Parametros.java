package com.stockeate.api.parametros.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parametros")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Parametros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parametro_id")
    private Long id;

    //Limitar si dentro de una compra de stock que hace un negocio puede haber productos provenientes de distintos proveedores
    @Column(nullable = false)
    private Boolean unaCompraDistintosProveedores;

    //Exigir que al cargar una venta se ingrese su forma de pago. 
    //Depende de si al negocio le interesa tener estadísticas en base a eso
    @Column(nullable = false)
    private Boolean exigirFormaDePagoAlCargarVenta;

    //Exigir que al cargar una venta se ingrese un cliente. 
    //Depende de si al negocio le interesa registrar siempre sus clientes.
    //Con negocios que trabajan por encargo suele ser necesario.
    @Column(nullable = false)
    private Boolean exigirClienteAlCargarVenta;

    //Permitir realizar una venta aunque el stock de alguno de los productos que conforma la misma es insuficiente
    //Depende de si al negocio le importa tener un control exhaustivo del stock.
    @Column(nullable = false)
    private Boolean permitirVenderSinStock;

    //Permitir que los usuarios de rol empleado puedan cargar compras de stock
    @Column(nullable = false)
    private Boolean empleadoPuedeCargarCompras;

    //Permitir que los usuarios de rol empleado puedan utilizar el ABM modificando las tablas del negocio
    @Column(nullable = false)
    private Boolean empleadoPuedeUtilizarAbm;

}