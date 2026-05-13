-- Esquema inicial

CREATE TABLE IF NOT EXISTS parametros (
    parametro_id BIGSERIAL PRIMARY KEY,
    unaCompraDistintosProveedores BOOLEAN NOT NULL,
    exigirFormaDePago BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS negocios (
    negocio_id BIGSERIAL PRIMARY KEY,
    nombre_negocio VARCHAR(100) NOT NULL UNIQUE,
    fecha_creacion DATE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    parametro_id BIGINT UNIQUE,
    CONSTRAINT fk_negocio_parametro FOREIGN KEY (parametro_id) REFERENCES parametros (parametro_id)
);

CREATE TABLE IF NOT EXISTS proveedores (
    proveedor_id BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    negocio_id BIGINT NOT NULL,
    CONSTRAINT fk_proveedor_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS categorias (
    categoria_id BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    fecha_creacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    negocio_id BIGINT NOT NULL,
    CONSTRAINT uq_categoria_negocio UNIQUE (descripcion, negocio_id),
    CONSTRAINT fk_categoria_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS productos (
    producto_id BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    fecha_creacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id BIGINT,
    proveedor_id BIGINT,
    negocio_id BIGINT NOT NULL,
    CONSTRAINT uq_producto_negocio UNIQUE (descripcion, negocio_id),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias (categoria_id),
    CONSTRAINT fk_producto_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedores (proveedor_id),
    CONSTRAINT fk_producto_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS clientes (
    cliente_id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    negocio_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS usuarios (
    usuario_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nombre_completo VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    ultimo_acceso TIMESTAMP,
    fecha_creacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    rol VARCHAR(50) NOT NULL,
    negocio_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS formas_pago (
    forma_id BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS compras (
    compra_id BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    usuario_id BIGINT NOT NULL,
    negocio_id BIGINT NOT NULL,
    CONSTRAINT fk_compra_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (usuario_id),
    CONSTRAINT fk_compra_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS ventas (
    venta_id BIGSERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL,
    cliente_id BIGINT NOT NULL,
    forma_id BIGINT,
    usuario_id BIGINT NOT NULL,
    negocio_id BIGINT NOT NULL,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (cliente_id),
    CONSTRAINT fk_venta_forma FOREIGN KEY (forma_id) REFERENCES formas_pago (forma_id),
    CONSTRAINT fk_venta_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (usuario_id),
    CONSTRAINT fk_venta_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (negocio_id)
);

CREATE TABLE IF NOT EXISTS precios (
    precio_id BIGSERIAL PRIMARY KEY,
    monto NUMERIC(19,4) NOT NULL,
    desde TIMESTAMP NOT NULL,
    hasta TIMESTAMP,
    producto_id BIGINT NOT NULL,
    CONSTRAINT fk_precio_producto FOREIGN KEY (producto_id) REFERENCES productos (producto_id)
);

CREATE TABLE IF NOT EXISTS items_compras (
    item_compra_id BIGSERIAL PRIMARY KEY,
    cantidad INTEGER NOT NULL,
    precio_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    compra_id BIGINT NOT NULL,
    CONSTRAINT fk_itemcompra_precio FOREIGN KEY (precio_id) REFERENCES precios (precio_id),
    CONSTRAINT fk_itemcompra_producto FOREIGN KEY (producto_id) REFERENCES productos (producto_id),
    CONSTRAINT fk_itemcompra_compra FOREIGN KEY (compra_id) REFERENCES compras (compra_id)
);

CREATE TABLE IF NOT EXISTS items_ventas (
    item_venta_id BIGSERIAL PRIMARY KEY,
    cantidad INTEGER NOT NULL,
    precio_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    venta_id BIGINT NOT NULL,
    CONSTRAINT fk_itemventa_precio FOREIGN KEY (precio_id) REFERENCES precios (precio_id),
    CONSTRAINT fk_itemventa_producto FOREIGN KEY (producto_id) REFERENCES productos (producto_id),
    CONSTRAINT fk_itemventa_venta FOREIGN KEY (venta_id) REFERENCES ventas (venta_id)
);

