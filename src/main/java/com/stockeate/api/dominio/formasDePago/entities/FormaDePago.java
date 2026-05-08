package com.stockeate.api.dominio.formasDePago.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formas_pago")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FormaDePago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forma_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String descripcion;

}
