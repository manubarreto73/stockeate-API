-- ============================================================
-- V2__seed_data.sql
-- Datos de prueba para Stockeate
-- Usa CTEs con RETURNING para evitar asumir IDs de secuencia
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
-- TODO EL RESTO EN UN SOLO CTE ENCADENADO
-- ------------------------------------------------------------
WITH param1 AS (
    INSERT INTO parametros (una_compra_distintos_proveedores, exigir_forma_de_pago)
    VALUES (false, true)
    RETURNING parametro_id
),
param2 AS (
    INSERT INTO parametros (una_compra_distintos_proveedores, exigir_forma_de_pago)
    VALUES (true, false)
    RETURNING parametro_id
),
negocio1 AS (
    INSERT INTO negocios (nombre_negocio, fecha_creacion, activo, parametro_id)
    SELECT 'Ferretería El Tornillo', '2023-01-15', true, parametro_id FROM param1
    RETURNING negocio_id
),
negocio2 AS (
    INSERT INTO negocios (nombre_negocio, fecha_creacion, activo, parametro_id)
    SELECT 'Librería El Saber', '2023-03-20', true, parametro_id FROM param2
    RETURNING negocio_id
),
u_admin1 AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'superadmin@stockeate.com', 'Super Admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-01-01', true, 'SUPERADMIN', negocio_id FROM negocio1
    RETURNING usuario_id
),
u_admin1b AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'admin@ferreteria.com', 'Carlos Gómez', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-01-15', true, 'ADMIN', negocio_id FROM negocio1
    RETURNING usuario_id
),
u_gerente1 AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'gerente@ferreteria.com', 'Laura Martínez', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-01-16', true, 'GERENTE', negocio_id FROM negocio1
    RETURNING usuario_id
),
u_empleado1a AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'empleado1@ferreteria.com', 'Marcos Rodríguez', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-02-01', true, 'EMPLEADO', negocio_id FROM negocio1
    RETURNING usuario_id
),
u_empleado1b AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'empleado2@ferreteria.com', 'Ana López', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-02-10', true, 'EMPLEADO', negocio_id FROM negocio1
    RETURNING usuario_id
),
u_admin2 AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'admin@libreria.com', 'Sofía Herrera', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-03-20', true, 'ADMIN', negocio_id FROM negocio2
    RETURNING usuario_id
),
u_gerente2 AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'gerente@libreria.com', 'Diego Fernández', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-03-21', true, 'GERENTE', negocio_id FROM negocio2
    RETURNING usuario_id
),
u_empleado2a AS (
    INSERT INTO usuarios (email, nombre_completo, password, ultimo_acceso, fecha_creacion, activo, rol, negocio_id)
    SELECT 'empleado1@libreria.com', 'Valentina Torres', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), '2023-04-01', true, 'EMPLEADO', negocio_id FROM negocio2
    RETURNING usuario_id
),
prov1 AS (
    INSERT INTO proveedores (descripcion, telefono, activo, negocio_id)
    SELECT 'Distribuidora MetalMax', '2214-1111', true, negocio_id FROM negocio1
    RETURNING proveedor_id
),
prov2 AS (
    INSERT INTO proveedores (descripcion, telefono, activo, negocio_id)
    SELECT 'Herramientas del Sur', '2214-2222', true, negocio_id FROM negocio1
    RETURNING proveedor_id
),
prov3 AS (
    INSERT INTO proveedores (descripcion, telefono, activo, negocio_id)
    SELECT 'Pinturas Colormix', '2214-3333', true, negocio_id FROM negocio1
    RETURNING proveedor_id
),
prov4 AS (
    INSERT INTO proveedores (descripcion, telefono, activo, negocio_id)
    SELECT 'Editorial Norma', '1145-4444', true, negocio_id FROM negocio2
    RETURNING proveedor_id
),
prov5 AS (
    INSERT INTO proveedores (descripcion, telefono, activo, negocio_id)
    SELECT 'Distribuidora Papel Feliz', '1145-5555', true, negocio_id FROM negocio2
    RETURNING proveedor_id
),
prov6 AS (
    INSERT INTO proveedores (descripcion, telefono, activo, negocio_id)
    SELECT 'Artística Kreativa', '1145-6666', true, negocio_id FROM negocio2
    RETURNING proveedor_id
),
cat1 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Herramientas', '2023-01-15', true, negocio_id FROM negocio1
    RETURNING categoria_id
),
cat2 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Pinturas', '2023-01-15', true, negocio_id FROM negocio1
    RETURNING categoria_id
),
cat3 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Fijaciones', '2023-01-15', true, negocio_id FROM negocio1
    RETURNING categoria_id
),
cat4 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Electricidad', '2023-01-15', true, negocio_id FROM negocio1
    RETURNING categoria_id
),
cat5 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Libros', '2023-03-20', true, negocio_id FROM negocio2
    RETURNING categoria_id
),
cat6 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Útiles', '2023-03-20', true, negocio_id FROM negocio2
    RETURNING categoria_id
),
cat7 AS (
    INSERT INTO categorias (descripcion, fecha_creacion, activo, negocio_id)
    SELECT 'Arte', '2023-03-20', true, negocio_id FROM negocio2
    RETURNING categoria_id
),
prod1 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Martillo 500g', 20, '2023-01-20', true, cat1.categoria_id, prov2.proveedor_id, negocio1.negocio_id
    FROM cat1, prov2, negocio1
    RETURNING producto_id
),
prod2 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Destornillador Phillips', 35, '2023-01-20', true, cat1.categoria_id, prov2.proveedor_id, negocio1.negocio_id
    FROM cat1, prov2, negocio1
    RETURNING producto_id
),
prod3 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Pintura Látex Blanca 4L', 15, '2023-01-20', true, cat2.categoria_id, prov3.proveedor_id, negocio1.negocio_id
    FROM cat2, prov3, negocio1
    RETURNING producto_id
),
prod4 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Pintura Látex Roja 4L', 10, '2023-01-20', true, cat2.categoria_id, prov3.proveedor_id, negocio1.negocio_id
    FROM cat2, prov3, negocio1
    RETURNING producto_id
),
prod5 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Tornillos 6x40 (caja)', 50, '2023-01-20', true, cat3.categoria_id, prov1.proveedor_id, negocio1.negocio_id
    FROM cat3, prov1, negocio1
    RETURNING producto_id
),
prod6 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Clavos 2" (kg)', 40, '2023-01-20', true, cat3.categoria_id, prov1.proveedor_id, negocio1.negocio_id
    FROM cat3, prov1, negocio1
    RETURNING producto_id
),
prod7 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Cable unipolar 2.5mm', 100, '2023-02-01', true, cat4.categoria_id, prov1.proveedor_id, negocio1.negocio_id
    FROM cat4, prov1, negocio1
    RETURNING producto_id
),
prod8 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Harry Potter T1', 25, '2023-03-25', true, cat5.categoria_id, prov4.proveedor_id, negocio2.negocio_id
    FROM cat5, prov4, negocio2
    RETURNING producto_id
),
prod9 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'El Principito', 30, '2023-03-25', true, cat5.categoria_id, prov4.proveedor_id, negocio2.negocio_id
    FROM cat5, prov4, negocio2
    RETURNING producto_id
),
prod10 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Cuaderno A4 tapa dura', 60, '2023-03-25', true, cat6.categoria_id, prov5.proveedor_id, negocio2.negocio_id
    FROM cat6, prov5, negocio2
    RETURNING producto_id
),
prod11 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Lapiceras x10', 80, '2023-03-25', true, cat6.categoria_id, prov5.proveedor_id, negocio2.negocio_id
    FROM cat6, prov5, negocio2
    RETURNING producto_id
),
prod12 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Set acuarelas 24 colores', 20, '2023-03-25', true, cat7.categoria_id, prov6.proveedor_id, negocio2.negocio_id
    FROM cat7, prov6, negocio2
    RETURNING producto_id
),
prod13 AS (
    INSERT INTO productos (descripcion, stock, fecha_creacion, activo, categoria_id, proveedor_id, negocio_id)
    SELECT 'Lienzo 30x40', 15, '2023-04-01', true, cat7.categoria_id, prov6.proveedor_id, negocio2.negocio_id
    FROM cat7, prov6, negocio2
    RETURNING producto_id
),
precio1_v AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 1800.00, '2023-01-20 00:00:00', '2023-06-01 00:00:00', producto_id FROM prod1
    RETURNING precio_id
),
precio1 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 2200.00, '2023-06-01 00:00:00', NULL, producto_id FROM prod1
    RETURNING precio_id
),
precio2_v AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 950.00, '2023-01-20 00:00:00', '2023-09-01 00:00:00', producto_id FROM prod2
    RETURNING precio_id
),
precio2 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 1100.00, '2023-09-01 00:00:00', NULL, producto_id FROM prod2
    RETURNING precio_id
),
precio3 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 3500.00, '2023-01-20 00:00:00', NULL, producto_id FROM prod3
    RETURNING precio_id
),
precio4 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 3700.00, '2023-01-20 00:00:00', NULL, producto_id FROM prod4
    RETURNING precio_id
),
precio5_v AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 600.00, '2023-01-20 00:00:00', '2024-01-01 00:00:00', producto_id FROM prod5
    RETURNING precio_id
),
precio5 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 750.00, '2024-01-01 00:00:00', NULL, producto_id FROM prod5
    RETURNING precio_id
),
precio6 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 400.00, '2023-01-20 00:00:00', NULL, producto_id FROM prod6
    RETURNING precio_id
),
precio7_v AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 1200.00, '2023-02-01 00:00:00', '2024-03-01 00:00:00', producto_id FROM prod7
    RETURNING precio_id
),
precio7 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 1500.00, '2024-03-01 00:00:00', NULL, producto_id FROM prod7
    RETURNING precio_id
),
precio8_v AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 4500.00, '2023-03-25 00:00:00', '2024-01-01 00:00:00', producto_id FROM prod8
    RETURNING precio_id
),
precio8 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 5200.00, '2024-01-01 00:00:00', NULL, producto_id FROM prod8
    RETURNING precio_id
),
precio9 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 2800.00, '2023-03-25 00:00:00', NULL, producto_id FROM prod9
    RETURNING precio_id
),
precio10_v AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 1500.00, '2023-03-25 00:00:00', '2023-12-01 00:00:00', producto_id FROM prod10
    RETURNING precio_id
),
precio10 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 1800.00, '2023-12-01 00:00:00', NULL, producto_id FROM prod10
    RETURNING precio_id
),
precio11 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 900.00, '2023-03-25 00:00:00', NULL, producto_id FROM prod11
    RETURNING precio_id
),
precio12 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 3200.00, '2023-03-25 00:00:00', NULL, producto_id FROM prod12
    RETURNING precio_id
),
precio13 AS (
    INSERT INTO precios (monto, desde, hasta, producto_id)
    SELECT 2500.00, '2023-04-01 00:00:00', NULL, producto_id FROM prod13
    RETURNING precio_id
),
cliente1 AS (
    INSERT INTO clientes (nombre_completo, telefono, negocio_id)
    SELECT 'Roberto Sánchez', '2214-9001', negocio_id FROM negocio1
    RETURNING cliente_id
),
cliente2 AS (
    INSERT INTO clientes (nombre_completo, telefono, negocio_id)
    SELECT 'Patricia Díaz', '2214-9002', negocio_id FROM negocio1
    RETURNING cliente_id
),
cliente3 AS (
    INSERT INTO clientes (nombre_completo, telefono, negocio_id)
    SELECT 'Constructora AYB', '2214-9003', negocio_id FROM negocio1
    RETURNING cliente_id
),
cliente4 AS (
    INSERT INTO clientes (nombre_completo, telefono, negocio_id)
    SELECT 'Martina Ruiz', '1145-8001', negocio_id FROM negocio2
    RETURNING cliente_id
),
cliente5 AS (
    INSERT INTO clientes (nombre_completo, telefono, negocio_id)
    SELECT 'Escuela N°12', '1145-8002', negocio_id FROM negocio2
    RETURNING cliente_id
),
cliente6 AS (
    INSERT INTO clientes (nombre_completo, telefono, negocio_id)
    SELECT 'Lucas Morales', '1145-8003', negocio_id FROM negocio2
    RETURNING cliente_id
),
compra1 AS (
    INSERT INTO compras (fecha, usuario_id, negocio_id)
    SELECT '2024-01-10', u_admin1b.usuario_id, negocio1.negocio_id FROM u_admin1b, negocio1
    RETURNING compra_id
),
compra2 AS (
    INSERT INTO compras (fecha, usuario_id, negocio_id)
    SELECT '2024-02-15', u_gerente1.usuario_id, negocio1.negocio_id FROM u_gerente1, negocio1
    RETURNING compra_id
),
compra3 AS (
    INSERT INTO compras (fecha, usuario_id, negocio_id)
    SELECT '2024-01-12', u_admin2.usuario_id, negocio2.negocio_id FROM u_admin2, negocio2
    RETURNING compra_id
),
compra4 AS (
    INSERT INTO compras (fecha, usuario_id, negocio_id)
    SELECT '2024-03-05', u_gerente2.usuario_id, negocio2.negocio_id FROM u_gerente2, negocio2
    RETURNING compra_id
),
ic1 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 10, precio1.precio_id, prod1.producto_id, compra1.compra_id FROM precio1, prod1, compra1
    RETURNING item_compra_id
),
ic2 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 20, precio2.precio_id, prod2.producto_id, compra1.compra_id FROM precio2, prod2, compra1
    RETURNING item_compra_id
),
ic3 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 30, precio5.precio_id, prod5.producto_id, compra1.compra_id FROM precio5, prod5, compra1
    RETURNING item_compra_id
),
ic4 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 50, precio7.precio_id, prod7.producto_id, compra2.compra_id FROM precio7, prod7, compra2
    RETURNING item_compra_id
),
ic5 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 5, precio3.precio_id, prod3.producto_id, compra2.compra_id FROM precio3, prod3, compra2
    RETURNING item_compra_id
),
ic6 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 15, precio8.precio_id, prod8.producto_id, compra3.compra_id FROM precio8, prod8, compra3
    RETURNING item_compra_id
),
ic7 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 20, precio9.precio_id, prod9.producto_id, compra3.compra_id FROM precio9, prod9, compra3
    RETURNING item_compra_id
),
ic8 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 30, precio10.precio_id, prod10.producto_id, compra4.compra_id FROM precio10, prod10, compra4
    RETURNING item_compra_id
),
ic9 AS (
    INSERT INTO items_compras (cantidad, precio_id, producto_id, compra_id)
    SELECT 40, precio11.precio_id, prod11.producto_id, compra4.compra_id FROM precio11, prod11, compra4
    RETURNING item_compra_id
),
venta1 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-01-15 10:30:00', cliente1.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Efectivo'),
           u_empleado1a.usuario_id, negocio1.negocio_id
    FROM cliente1, u_empleado1a, negocio1
    RETURNING venta_id
),
venta2 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-01-20 14:00:00', cliente2.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Tarjeta de débito'),
           u_empleado1a.usuario_id, negocio1.negocio_id
    FROM cliente2, u_empleado1a, negocio1
    RETURNING venta_id
),
venta3 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-02-01 09:15:00', cliente3.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Transferencia bancaria'),
           u_empleado1b.usuario_id, negocio1.negocio_id
    FROM cliente3, u_empleado1b, negocio1
    RETURNING venta_id
),
venta4 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-03-10 11:00:00', cliente1.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Tarjeta de crédito'),
           u_empleado1a.usuario_id, negocio1.negocio_id
    FROM cliente1, u_empleado1a, negocio1
    RETURNING venta_id
),
venta5 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-01-18 10:00:00', cliente4.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Efectivo'),
           u_empleado2a.usuario_id, negocio2.negocio_id
    FROM cliente4, u_empleado2a, negocio2
    RETURNING venta_id
),
venta6 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-02-05 16:30:00', cliente5.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Transferencia bancaria'),
           u_empleado2a.usuario_id, negocio2.negocio_id
    FROM cliente5, u_empleado2a, negocio2
    RETURNING venta_id
),
venta7 AS (
    INSERT INTO ventas (fecha_hora, cliente_id, forma_id, usuario_id, negocio_id)
    SELECT '2024-03-12 12:00:00', cliente6.cliente_id,
           (SELECT forma_id FROM formas_pago WHERE descripcion = 'Mercado Pago'),
           u_empleado2a.usuario_id, negocio2.negocio_id
    FROM cliente6, u_empleado2a, negocio2
    RETURNING venta_id
),
iv1 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 2, precio1.precio_id, prod1.producto_id, venta1.venta_id FROM precio1, prod1, venta1
    RETURNING item_venta_id
),
iv2 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio2.precio_id, prod2.producto_id, venta1.venta_id FROM precio2, prod2, venta1
    RETURNING item_venta_id
),
iv3 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 3, precio5.precio_id, prod5.producto_id, venta2.venta_id FROM precio5, prod5, venta2
    RETURNING item_venta_id
),
iv4 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 2, precio6.precio_id, prod6.producto_id, venta2.venta_id FROM precio6, prod6, venta2
    RETURNING item_venta_id
),
iv5 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 2, precio3.precio_id, prod3.producto_id, venta3.venta_id FROM precio3, prod3, venta3
    RETURNING item_venta_id
),
iv6 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio4.precio_id, prod4.producto_id, venta3.venta_id FROM precio4, prod4, venta3
    RETURNING item_venta_id
),
iv7 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 10, precio7.precio_id, prod7.producto_id, venta3.venta_id FROM precio7, prod7, venta3
    RETURNING item_venta_id
),
iv8 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio1.precio_id, prod1.producto_id, venta4.venta_id FROM precio1, prod1, venta4
    RETURNING item_venta_id
),
iv9 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio8.precio_id, prod8.producto_id, venta5.venta_id FROM precio8, prod8, venta5
    RETURNING item_venta_id
),
iv10 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio9.precio_id, prod9.producto_id, venta5.venta_id FROM precio9, prod9, venta5
    RETURNING item_venta_id
),
iv11 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 10, precio10.precio_id, prod10.producto_id, venta6.venta_id FROM precio10, prod10, venta6
    RETURNING item_venta_id
),
iv12 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 10, precio11.precio_id, prod11.producto_id, venta6.venta_id FROM precio11, prod11, venta6
    RETURNING item_venta_id
),
iv13 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio12.precio_id, prod12.producto_id, venta7.venta_id FROM precio12, prod12, venta7
    RETURNING item_venta_id
),
iv14 AS (
    INSERT INTO items_ventas (cantidad, precio_id, producto_id, venta_id)
    SELECT 1, precio13.precio_id, prod13.producto_id, venta7.venta_id FROM precio13, prod13, venta7
    RETURNING item_venta_id
)
SELECT 'Seed completado exitosamente' AS resultado;
