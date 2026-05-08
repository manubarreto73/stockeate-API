-- Esquema inicial

-- Usuario
CREATE TABLE usuarios (
    id            BIGSERIAL     PRIMARY KEY,
    email         VARCHAR(255)  NOT NULL UNIQUE,
    password      VARCHAR(255)  NOT NULL,
    ultimo_acceso TIMESTAMP,
    activo        BOOLEAN       NOT NULL DEFAULT TRUE
);

-- Datos iniciales

