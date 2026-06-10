package com.stockeate.api.parametros.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

@Service
public class PermisosService {

    public void verificarCompras(Usuario autenticado) {
        if (autenticado.getRol() != RolUsuario.EMPLEADO) return;
        if (!autenticado.getNegocio().getParametros().getEmpleadoPuedeCargarCompras())
            throw new AccessDeniedException("El empleado no tiene permiso para cargar compras");
    }

    public void verificarAbm(Usuario autenticado) {
        if (autenticado.getRol() != RolUsuario.EMPLEADO) return;
        if (!autenticado.getNegocio().getParametros().getEmpleadoPuedeUtilizarAbm())
            throw new AccessDeniedException("El empleado no tiene permiso para utilizar el ABM");
    }

}
