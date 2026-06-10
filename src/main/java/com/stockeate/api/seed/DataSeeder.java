package com.stockeate.api.seed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.stockeate.api.dominio.categoria.entities.Categoria;
import com.stockeate.api.dominio.categoria.entities.Subcategoria;
import com.stockeate.api.dominio.categoria.repositories.CategoriaRepository;
import com.stockeate.api.dominio.categoria.repositories.SubcategoriaRepository;
import com.stockeate.api.dominio.clientes.entities.Cliente;
import com.stockeate.api.dominio.clientes.repositories.ClienteRepository;
import com.stockeate.api.dominio.compras.entities.Compra;
import com.stockeate.api.dominio.compras.entities.ItemCompra;
import com.stockeate.api.dominio.compras.repositories.CompraRepository;
import com.stockeate.api.dominio.formasDePago.entities.FormaDePago;
import com.stockeate.api.dominio.formasDePago.repositories.FormasDepagoRepository;
import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.negocios.repository.NegocioRepository;
import com.stockeate.api.dominio.precios.entities.Precio;
import com.stockeate.api.dominio.precios.entities.TipoPrecio;
import com.stockeate.api.dominio.precios.repositories.PrecioRespository;
import com.stockeate.api.dominio.productos.entities.Producto;
import com.stockeate.api.dominio.productos.repositories.ProductoRepository;
import com.stockeate.api.dominio.proveedores.entities.Proveedor;
import com.stockeate.api.dominio.proveedores.repositories.ProveedorRepository;
import com.stockeate.api.dominio.usuarios.entities.RolUsuario;
import com.stockeate.api.dominio.usuarios.entities.Usuario;
import com.stockeate.api.dominio.usuarios.repository.UsuarioRepository;
import com.stockeate.api.dominio.ventas.entities.ItemVenta;
import com.stockeate.api.dominio.ventas.entities.Venta;
import com.stockeate.api.dominio.ventas.repositories.VentaRepository;
import com.stockeate.api.parametros.entities.Parametros;
import com.stockeate.api.parametros.repositories.ParametrosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

/**
 * Carga ~2700 registros de prueba al iniciar con el perfil "seed".
 * Ejecutar con: --spring.profiles.active=seed
 */
@Slf4j
@Component
@Profile("seed")
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final NegocioRepository negocioRepository;
    private final ParametrosRepository parametrosRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final PrecioRespository precioRepository;
    private final ClienteRepository clienteRepository;
    private final FormasDepagoRepository formasDepagoRepository;
    private final CompraRepository compraRepository;
    private final VentaRepository ventaRepository;
    private final PasswordEncoder passwordEncoder;

    // No son final para que Lombok no los incluya en el constructor de inyección
    private Faker faker = new Faker(new Locale("es"));
    private Random rng = new Random(42L);

    private record SeedProducto(Producto producto, Precio precioCompra, Precio precioVenta) {}

    private static final String[][] CATS = {
        {"Electrónica",  "Celulares",   "Computadoras",  "Televisores"},
        {"Ropa",         "Remeras",     "Pantalones",    "Camperas"},
        {"Alimentos",    "Lácteos",     "Fiambres",      "Conservas"},
        {"Bebidas",      "Gaseosas",    "Cervezas",      "Jugos"},
        {"Limpieza",     "Detergentes", "Lavandinas",    "Desinfectantes"},
        {"Herramientas", "Manuales",    "Eléctricas",    "Jardinería"},
        {"Calzado",      "Zapatillas",  "Sandalias",     "Botas"},
    };

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (negocioRepository.count() > 0) {
            log.info("Seed omitido: la base de datos ya tiene datos.");
            return;
        }
        log.info("Iniciando seed de datos de prueba...");
        List<FormaDePago> formasDePago = crearFormasDePago();
        for (int i = 1; i <= 3; i++) {
            seedNegocio(i, formasDePago);
        }
        log.info("Seed completado exitosamente.");
    }

    // ─── Formas de pago (globales, no por negocio) ───────────────────────────

    private List<FormaDePago> crearFormasDePago() {
        List<FormaDePago> result = new ArrayList<>();
        for (String d : List.of("Efectivo", "Tarjeta de crédito", "Tarjeta de débito", "Transferencia bancaria", "MercadoPago")) {
            result.add(formasDepagoRepository.save(FormaDePago.builder().descripcion(d).build()));
        }
        return result;
    }

    // ─── Negocio ─────────────────────────────────────────────────────────────

    private void seedNegocio(int idx, List<FormaDePago> formasDePago) {
        Parametros params = parametrosRepository.save(Parametros.builder()
            .unaCompraDistintosProveedores(true)
            .exigirFormaDePagoAlCargarVenta(false)
            .exigirClienteAlCargarVenta(false)
            .permitirVenderSinStock(false)
            .empleadoPuedeCargarCompras(true)
            .empleadoPuedeUtilizarAbm(false)
            .build());

        Negocio negocio = negocioRepository.save(Negocio.builder()
            .nombreNegocio("Negocio " + idx + " - " + faker.company().name())
            .fechaCreacion(LocalDateTime.now().minusDays(365 + rng.nextInt(365)))
            .activo(true)
            .parametros(params)
            .build());

        List<Usuario> usuarios      = crearUsuarios(negocio, idx);
        List<Categoria> categorias  = crearCategorias(negocio);
        List<Proveedor> proveedores = crearProveedores(negocio);
        List<SeedProducto> prods    = crearProductos(negocio, categorias, proveedores);
        List<Cliente> clientes      = crearClientes(negocio);

        List<Precio> preciosCompra = prods.stream().map(SeedProducto::precioCompra).toList();
        List<Precio> preciosVenta  = prods.stream().map(SeedProducto::precioVenta).toList();

        crearCompras(negocio, usuarios, preciosCompra);
        crearVentas(negocio, usuarios, clientes, preciosVenta, formasDePago);

        log.info("  → '{}': {} usuarios, {} categorías, {} proveedores, {} productos, {} clientes, 50 compras, 100 ventas.",
            negocio.getNombreNegocio(), usuarios.size(), categorias.size(), proveedores.size(), prods.size(), clientes.size());
    }

    // ─── Usuarios ─────────────────────────────────────────────────────────────

    private List<Usuario> crearUsuarios(Negocio negocio, int idx) {
        String pwd = passwordEncoder.encode("password123");
        LocalDateTime base = negocio.getFechaCreacion();
        List<Usuario> result = new ArrayList<>();

        result.add(usuarioRepository.save(Usuario.builder()
            .email("admin" + idx + "@stockeate.test")
            .nombreCompleto(faker.name().fullName())
            .password(pwd).fechaCreacion(base).activo(true)
            .rol(RolUsuario.ADMIN).negocio(negocio).build()));

        result.add(usuarioRepository.save(Usuario.builder()
            .email("gerente" + idx + "@stockeate.test")
            .nombreCompleto(faker.name().fullName())
            .password(pwd).fechaCreacion(base.plusDays(1)).activo(true)
            .rol(RolUsuario.GERENTE).negocio(negocio).build()));

        for (int i = 1; i <= 5; i++) {
            result.add(usuarioRepository.save(Usuario.builder()
                .email("empleado" + idx + "u" + i + "@stockeate.test")
                .nombreCompleto(faker.name().fullName())
                .password(pwd)
                .fechaCreacion(base.plusDays(rng.nextInt(30)))
                .activo(i <= 4).rol(RolUsuario.EMPLEADO).negocio(negocio).build()));
        }
        return result;
    }

    // ─── Categorías y subcategorías ──────────────────────────────────────────

    private List<Categoria> crearCategorias(Negocio negocio) {
        List<Categoria> result = new ArrayList<>();
        for (String[] row : CATS) {
            Categoria cat = categoriaRepository.save(Categoria.builder()
                .descripcion(row[0])
                .fechaCreacion(negocio.getFechaCreacion().plusDays(rng.nextInt(10)))
                .activo(true).negocio(negocio).build());

            for (int i = 1; i < row.length; i++) {
                subcategoriaRepository.save(Subcategoria.builder()
                    .descripcion(row[i])
                    .fechaCreacion(cat.getFechaCreacion().plusDays(1))
                    .activo(true).categoria(cat).build());
            }
            result.add(cat);
        }
        return result;
    }

    // ─── Proveedores ──────────────────────────────────────────────────────────

    private List<Proveedor> crearProveedores(Negocio negocio) {
        List<Proveedor> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String tel = faker.phoneNumber().phoneNumber();
            result.add(proveedorRepository.save(Proveedor.builder()
                .descripcion(faker.company().name())
                .telefono(tel.length() > 20 ? tel.substring(0, 20) : tel)
                .activo(true).negocio(negocio).build()));
        }
        return result;
    }

    // ─── Productos + precios ──────────────────────────────────────────────────

    private List<SeedProducto> crearProductos(Negocio negocio, List<Categoria> categorias, List<Proveedor> proveedores) {
        List<SeedProducto> result = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            String desc = faker.commerce().productName() + " #" + (i + 1);
            if (desc.length() > 100) desc = desc.substring(0, 100);

            Producto prod = productoRepository.save(Producto.builder()
                .descripcion(desc)
                .stock(rng.nextInt(200) + 5)
                .fechaCreacion(negocio.getFechaCreacion().plusDays(rng.nextInt(60)))
                .activo(true)
                .categoria(categorias.get(rng.nextInt(categorias.size())))
                .proveedor(rng.nextBoolean() ? proveedores.get(rng.nextInt(proveedores.size())) : null)
                .negocio(negocio)
                .build());

            BigDecimal compraM = BigDecimal.valueOf(rng.nextInt(9900) + 100L);
            BigDecimal ventaM  = compraM.multiply(BigDecimal.valueOf(1.2 + rng.nextDouble() * 0.5))
                                        .setScale(2, RoundingMode.HALF_UP);

            Precio pc = precioRepository.save(Precio.builder()
                .monto(compraM).tipo(TipoPrecio.COMPRA)
                .desde(prod.getFechaCreacion()).producto(prod).build());

            Precio pv = precioRepository.save(Precio.builder()
                .monto(ventaM).tipo(TipoPrecio.VENTA)
                .desde(prod.getFechaCreacion()).producto(prod).build());

            result.add(new SeedProducto(prod, pc, pv));
        }
        return result;
    }

    // ─── Clientes ─────────────────────────────────────────────────────────────

    private List<Cliente> crearClientes(Negocio negocio) {
        List<Cliente> result = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            String tel = faker.phoneNumber().phoneNumber();
            result.add(clienteRepository.save(Cliente.builder()
                .nombreCompleto(faker.name().fullName())
                .telefono(rng.nextBoolean() ? (tel.length() > 20 ? tel.substring(0, 20) : tel) : null)
                .fechaCreacion(negocio.getFechaCreacion().plusDays(rng.nextInt(90)))
                .activo(true).negocio(negocio).build()));
        }
        return result;
    }

    // ─── Compras ──────────────────────────────────────────────────────────────

    private void crearCompras(Negocio negocio, List<Usuario> usuarios, List<Precio> preciosCompra) {
        for (int i = 0; i < 50; i++) {
            LocalDateTime fecha = negocio.getFechaCreacion()
                .plusDays(rng.nextInt(300)).plusHours(rng.nextInt(24));
            boolean recibida = rng.nextBoolean();

            Compra compra = Compra.builder()
                .fechaCreacion(fecha)
                .recibida(recibida)
                .fechaRecepcion(recibida ? fecha.plusDays(rng.nextInt(7) + 1) : null)
                .compradoPor(usuarios.get(rng.nextInt(usuarios.size())))
                .negocio(negocio)
                .build();

            List<ItemCompra> items = new ArrayList<>();
            for (int j = 0; j < rng.nextInt(4) + 1; j++) {
                Precio p = preciosCompra.get(rng.nextInt(preciosCompra.size()));
                items.add(ItemCompra.builder()
                    .cantidad(rng.nextInt(30) + 1)
                    .precio(p).producto(p.getProducto()).compra(compra).build());
            }
            compra.setItems(items);
            compraRepository.save(compra);
        }
    }

    // ─── Ventas ───────────────────────────────────────────────────────────────

    private void crearVentas(Negocio negocio, List<Usuario> usuarios, List<Cliente> clientes,
                              List<Precio> preciosVenta, List<FormaDePago> formasDePago) {
        for (int i = 0; i < 100; i++) {
            LocalDateTime fecha = negocio.getFechaCreacion()
                .plusDays(rng.nextInt(300)).plusHours(rng.nextInt(24));

            Venta venta = Venta.builder()
                .fechaHora(fecha)
                .cliente(clientes.get(rng.nextInt(clientes.size())))
                .formaDePago(rng.nextBoolean() ? formasDePago.get(rng.nextInt(formasDePago.size())) : null)
                .vendidoPor(usuarios.get(rng.nextInt(usuarios.size())))
                .negocio(negocio)
                .build();

            List<ItemVenta> items = new ArrayList<>();
            for (int j = 0; j < rng.nextInt(5) + 1; j++) {
                Precio p = preciosVenta.get(rng.nextInt(preciosVenta.size()));
                items.add(ItemVenta.builder()
                    .cantidad(rng.nextInt(10) + 1)
                    .precio(p).producto(p.getProducto()).venta(venta).build());
            }
            venta.setItems(items);
            ventaRepository.save(venta);
        }
    }
}
