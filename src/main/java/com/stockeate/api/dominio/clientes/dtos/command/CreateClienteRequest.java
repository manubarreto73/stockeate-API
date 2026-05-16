package com.stockeate.api.dominio.clientes.dtos.command;

import com.stockeate.api.dominio.clientes.entities.Cliente;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateClienteRequest {
    
    private String nombreCompleto;
    private String telefono;

    public Cliente toEntity() {
        return Cliente.builder()
            .nombreCompleto(nombreCompleto)
            .telefono(telefono)
            .build();
    }

    public static CreateClienteRequest from (Cliente cliente) {
        return CreateClienteRequest.builder()
            .nombreCompleto(cliente.getNombreCompleto())
            .telefono(cliente.getTelefono())
            .build();
    }

}
