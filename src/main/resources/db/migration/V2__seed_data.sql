-- ============================================================
-- V2__seed_data.sql
-- ============================================================

-- ------------------------------------------------------------
-- FORMAS DE PAGO
-- ------------------------------------------------------------

INSERT INTO formas_pago (descripcion) VALUES
    ('Efectivo'),
    ('Tarjeta de débito'),
    ('Tarjeta de crédito'),
    ('Transferencia bancaria'),
    ('Mercado Pago');

-- ------------------------------------------------------------
-- PARAMETROS
-- ------------------------------------------------------------

WITH param1 AS (
    INSERT INTO parametros (
        exigir_forma_de_pago,
        una_compra_distintos_proveedores
    )
    VALUES (
        true,
        false
    )
    RETURNING parametro_id
),

param2 AS (
    INSERT INTO parametros (
        exigir_forma_de_pago,
        una_compra_distintos_proveedores
    )
    VALUES (
        false,
        true
    )
    RETURNING parametro_id
),

-- ------------------------------------------------------------
-- NEGOCIOS
-- ------------------------------------------------------------

negocio1 AS (
    INSERT INTO negocios (
        nombre_negocio,
        fecha_creacion,
        activo,
        parametro_id
    )
    SELECT
        'Ferretería El Tornillo',
        '2023-01-15',
        true,
        parametro_id
    FROM param1
    RETURNING negocio_id
),

negocio2 AS (
    INSERT INTO negocios (
        nombre_negocio,
        fecha_creacion,
        activo,
        parametro_id
    )
    SELECT
        'Librería El Saber',
        '2023-03-20',
        true,
        parametro_id
    FROM param2
    RETURNING negocio_id
),

-- ------------------------------------------------------------
-- USUARIOS
-- ------------------------------------------------------------

u_admin1 AS (
    INSERT INTO usuarios (
        email,
        nombre_completo,
        password,
        ultimo_acceso,
        fecha_creacion,
        activo,
        rol,
        negocio_id
    )
    SELECT
        'admin@ferreteria.com',
        'Carlos Gómez',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        NOW(),
        '2023-01-15',
        true,
        'ADMIN',
        negocio_id
    FROM negocio1
    RETURNING usuario_id
),

u_empleado1 AS (
    INSERT INTO usuarios (
        email,
        nombre_completo,
        password,
        ultimo_acceso,
        fecha_creacion,
        activo,
        rol,
        negocio_id
    )
    SELECT
        'empleado@ferreteria.com',
        'Marcos Rodríguez',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        NOW(),
        '2023-01-20',
        true,
        'EMPLEADO',
        negocio_id
    FROM negocio1
    RETURNING usuario_id
),

u_admin2 AS (
    INSERT INTO usuarios (
        email,
        nombre_completo,
        password,
        ultimo_acceso,
        fecha_creacion,
        activo,
        rol,
        negocio_id
    )
    SELECT
        'admin@libreria.com',
        'Sofía Herrera',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        NOW(),
        '2023-03-20',
        true,
        'ADMIN',
        negocio_id
    FROM negocio2
    RETURNING usuario_id
),

-- ------------------------------------------------------------
-- PROVEEDORES
-- ------------------------------------------------------------

prov1 AS (
    INSERT INTO proveedores (
        descripcion,
        telefono,
        activo,
        negocio_id
    )
    SELECT
        'Distribuidora MetalMax',
        '2214-1111',
        true,
        negocio_id
    FROM negocio1
    RETURNING proveedor_id
),

prov2 AS (
    INSERT INTO proveedores (
        descripcion,
        telefono,
        activo,
        negocio_id
    )
    SELECT
        'Editorial Norma',
        '1145-4444',
        true,
        negocio_id
    FROM negocio2
    RETURNING proveedor_id
),

-- ------------------------------------------------------------
-- CATEGORIAS
-- ------------------------------------------------------------

cat1 AS (
    INSERT INTO categorias (
        descripcion,
        fecha_creacion,
        activo,
        negocio_id
    )
    SELECT
        'Herramientas',
        '2023-01-15',
        true,
        negocio_id
    FROM negocio1
    RETURNING categoria_id
),

cat2 AS (
    INSERT INTO categorias (
        descripcion,
        fecha_creacion,
        activo,
        negocio_id
    )
    SELECT
        'Libros',
        '2023-03-20',
        true,
        negocio_id
    FROM negocio2
    RETURNING categoria_id
),

-- ------------------------------------------------------------
-- PRODUCTOS
-- ------------------------------------------------------------

prod1 AS (
    INSERT INTO productos (
        descripcion,
        stock,
        fecha_creacion,
        activo,
        categoria_id,
        proveedor_id,
        negocio_id
    )
    SELECT
        'Martillo 500g',
        20,
        '2023-01-20',
        true,
        cat1.categoria_id,
        prov1.proveedor_id,
        negocio1.negocio_id
    FROM cat1, prov1, negocio1
    RETURNING producto_id
),

prod2 AS (
    INSERT INTO productos (
        descripcion,
        stock,
        fecha_creacion,
        activo,
        categoria_id,
        proveedor_id,
        negocio_id
    )
    SELECT
        'El Principito',
        30,
        '2023-03-25',
        true,
        cat2.categoria_id,
        prov2.proveedor_id,
        negocio2.negocio_id
    FROM cat2, prov2, negocio2
    RETURNING producto_id
),

-- ------------------------------------------------------------
-- PRECIOS
-- ------------------------------------------------------------

precio1_compra AS (
    INSERT INTO precios (
        monto,
        desde,
        hasta,
        tipo,
        producto_id
    )
    SELECT
        1500.00,
        '2023-01-20',
        NULL,
        'COMPRA',
        producto_id
    FROM prod1
    RETURNING precio_id
),

precio1_venta AS (
    INSERT INTO precios (
        monto,
        desde,
        hasta,
        tipo,
        producto_id
    )
    SELECT
        2200.00,
        '2023-01-20',
        NULL,
        'VENTA',
        producto_id
    FROM prod1
    RETURNING precio_id
),

precio2_compra AS (
    INSERT INTO precios (
        monto,
        desde,
        hasta,
        tipo,
        producto_id
    )
    SELECT
        1800.00,
        '2023-03-25',
        NULL,
        'COMPRA',
        producto_id
    FROM prod2
    RETURNING precio_id
),

precio2_venta AS (
    INSERT INTO precios (
        monto,
        desde,
        hasta,
        tipo,
        producto_id
    )
    SELECT
        2800.00,
        '2023-03-25',
        NULL,
        'VENTA',
        producto_id
    FROM prod2
    RETURNING precio_id
),

-- ------------------------------------------------------------
-- CLIENTES
-- ------------------------------------------------------------

cliente1 AS (
    INSERT INTO clientes (
        nombre_completo,
        telefono,
        fecha_creacion,
        activo,
        negocio_id
    )
    SELECT
        'Roberto Sánchez',
        '2214-9001',
        '2023-01-25',
        true,
        negocio_id
    FROM negocio1
    RETURNING cliente_id
),

cliente2 AS (
    INSERT INTO clientes (
        nombre_completo,
        telefono,
        fecha_creacion,
        activo,
        negocio_id
    )
    SELECT
        'Martina Ruiz',
        '1145-8001',
        '2023-03-28',
        true,
        negocio_id
    FROM negocio2
    RETURNING cliente_id
),

-- ------------------------------------------------------------
-- COMPRAS
-- ------------------------------------------------------------

compra1 AS (
    INSERT INTO compras (
        recibida,
        fecha_carga,
        fecha_recepcion,
        usuario_id,
        negocio_id
    )
    SELECT
        true,
        '2024-01-10 09:00:00',
        '2024-01-11 12:00:00',
        u_admin1.usuario_id,
        negocio1.negocio_id
    FROM u_admin1, negocio1
    RETURNING compra_id
),

compra2 AS (
    INSERT INTO compras (
        recibida,
        fecha_carga,
        fecha_recepcion,
        usuario_id,
        negocio_id
    )
    SELECT
        true,
        '2024-02-05 10:00:00',
        '2024-02-06 13:00:00',
        u_admin2.usuario_id,
        negocio2.negocio_id
    FROM u_admin2, negocio2
    RETURNING compra_id
),

-- ------------------------------------------------------------
-- ITEMS COMPRA
-- ------------------------------------------------------------

ic1 AS (
    INSERT INTO items_compras (
        cantidad,
        precio_id,
        producto_id,
        compra_id
    )
    SELECT
        10,
        precio1_compra.precio_id,
        prod1.producto_id,
        compra1.compra_id
    FROM precio1_compra, prod1, compra1
    RETURNING item_compra_id
),

ic2 AS (
    INSERT INTO items_compras (
        cantidad,
        precio_id,
        producto_id,
        compra_id
    )
    SELECT
        20,
        precio2_compra.precio_id,
        prod2.producto_id,
        compra2.compra_id
    FROM precio2_compra, prod2, compra2
    RETURNING item_compra_id
),

-- ------------------------------------------------------------
-- VENTAS
-- ------------------------------------------------------------

venta1 AS (
    INSERT INTO ventas (
        fecha_hora,
        cliente_id,
        forma_id,
        usuario_id,
        negocio_id
    )
    SELECT
        '2024-01-15 10:30:00',
        cliente1.cliente_id,
        (
            SELECT forma_id
            FROM formas_pago
            WHERE descripcion = 'Efectivo'
        ),
        u_empleado1.usuario_id,
        negocio1.negocio_id
    FROM cliente1, u_empleado1, negocio1
    RETURNING venta_id
),

venta2 AS (
    INSERT INTO ventas (
        fecha_hora,
        cliente_id,
        forma_id,
        usuario_id,
        negocio_id
    )
    SELECT
        '2024-03-10 11:00:00',
        cliente2.cliente_id,
        (
            SELECT forma_id
            FROM formas_pago
            WHERE descripcion = 'Mercado Pago'
        ),
        u_admin2.usuario_id,
        negocio2.negocio_id
    FROM cliente2, u_admin2, negocio2
    RETURNING venta_id
),

-- ------------------------------------------------------------
-- ITEMS VENTA
-- ------------------------------------------------------------

iv1 AS (
    INSERT INTO items_ventas (
        cantidad,
        precio_id,
        producto_id,
        venta_id
    )
    SELECT
        2,
        precio1_venta.precio_id,
        prod1.producto_id,
        venta1.venta_id
    FROM precio1_venta, prod1, venta1
    RETURNING item_venta_id
),

iv2 AS (
    INSERT INTO items_ventas (
        cantidad,
        precio_id,
        producto_id,
        venta_id
    )
    SELECT
        1,
        precio2_venta.precio_id,
        prod2.producto_id,
        venta2.venta_id
    FROM precio2_venta, prod2, venta2
    RETURNING item_venta_id
)

SELECT 'Seed completado correctamente' AS resultado;