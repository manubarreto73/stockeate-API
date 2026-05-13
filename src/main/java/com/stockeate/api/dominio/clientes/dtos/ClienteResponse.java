package com.stockeate.api.dominio.clientes.dtos;

import com.stockeate.api.dominio.clientes.entities.Cliente;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ClienteResponse {

    private Long id;
    private String nombreCompleto;
    private String telefono;

    public static ClienteResponse from(Cliente cliente) {
        return ClienteResponse.builder()
            .id(cliente.getId())
            .nombreCompleto(cliente.getNombreCompleto())
            .telefono(cliente.getTelefono())
            .build();
    }

}
