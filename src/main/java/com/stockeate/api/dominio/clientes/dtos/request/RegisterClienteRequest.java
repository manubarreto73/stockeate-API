package com.stockeate.api.dominio.clientes.dtos.request;

import com.stockeate.api.dominio.clientes.dtos.command.CreateClienteRequest;
import com.stockeate.api.dominio.clientes.entities.Cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegisterClienteRequest {
    
    @NotBlank
    @Size(max = 100, message = "El nombre del cliente no puede tener más de 100 caracteres")
    private String nombreCompleto;

    @Size(min = 7, max = 20, message = "El telefono debe tener entre 7 y 20 caracteres")
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
