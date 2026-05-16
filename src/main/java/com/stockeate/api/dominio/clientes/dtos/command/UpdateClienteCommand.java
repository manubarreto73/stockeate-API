package com.stockeate.api.dominio.clientes.dtos.command;

import com.stockeate.api.dominio.clientes.entities.Cliente;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateClienteCommand {
    
    private String nombreCompleto;
    private String telefono;

    public Cliente update (Cliente cliente) {
        cliente.setNombreCompleto(nombreCompleto);
        cliente.setTelefono(telefono);
        return cliente;
    } 

    public Boolean hasChanges (Cliente cliente) {
        return !(nombreCompleto.equals(cliente.getNombreCompleto())) ||
                telefono.equals(cliente.getTelefono());
    }

    public static UpdateClienteCommand from (Cliente cliente) {
        return UpdateClienteCommand.builder()
            .nombreCompleto(cliente.getNombreCompleto())
            .telefono(cliente.getTelefono())
            .build();
    }

}
