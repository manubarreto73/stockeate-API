package com.stockeate.api.dominio.negocios.entities;

import java.util.List;

import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.productos.entities.Categoria;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;
import com.stockeate.api.dominio.sesiones.entities.Usuario;
import com.stockeate.api.dominio.ventas.entities.Venta;
import com.stockeate.api.parametros.Parametros;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "negocios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Negocio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negocio_id")
    private Long id;

    @Column(name = "nombre_negocio", nullable = false, unique = true, length = 100)
    private String nombreNegocio;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Producto> productos;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Compra> compras;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Proveedor> proveedores;

    @OneToMany(mappedBy = "negocio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Venta> ventas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parametro_id")
    private Parametros parametros;

}
