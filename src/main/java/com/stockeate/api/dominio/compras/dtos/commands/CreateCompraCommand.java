package com.stockeate.api.dominio.compras.dtos.commands;

import java.util.List;

import com.stockeate.api.dominio.compras.dtos.request.RegisterItemRequest;
import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

import lombok.Value;

@Value
public class CreateCompraCommand {
    
    Negocio negocio;
    Usuario usuarioComprador;
    List<RegisterItemRequest> items;

    public Compra toEntity() {
        return Compra.builder()
            .negocio(negocio)
            .compradoPor(usuarioComprador)
            .build();
    }

}
