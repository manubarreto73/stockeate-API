package com.stockeate.api.parametros;

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

    //Exigir que al cargar una venta se ingrese su forma de pago. Depende de si al negocio le interesa tener estadísticas en base a eso
    @Column(nullable = false)
    private Boolean exigirFormaDePago;

}