# Endpoints

---

## Negocios

---

**GET** `/api/negocios`
**Obtener negocio**
- Parámetros: ninguno
- Requiere: autenticación (Bearer token)
- Response `200`:
```json
{
  "nombreNegocio": "string",
  "fechaCreacion": "datetime"
}
```

---

**PUT** `/api/negocios`
**Actualizar nombre del negocio**
- Requiere: rol ADMIN
- Body:
```json
{
  "nombreNegocio": "string (requerido, máx 100)"
}
```
- Response `200`:
```json
{
  "nombreNegocio": "string",
  "fechaCreacion": "datetime"
}
```

---

## Usuarios

---

**GET** `/api/usuarios/roles`
**Obtener roles disponibles**
- Parámetros: ninguno
- Autenticación: no requerida
- Response `200`: `["ADMIN", "EMPLEADO"]`

---

**GET** `/api/usuarios`
**Listar usuarios del negocio (paginado)**
- Requiere: rol ADMIN
- Query params:
  - `page` (default: 0)
  - `sortBy` (default: `"fechaCreacion"`)
  - `sortDir` (default: `"asc"`)
- Response `200`:
```json
{
  "content": [
    {
      "usuarioId": 1,
      "email": "string",
      "nombreCompleto": "string",
      "ultimoAcceso": "datetime | null",
      "fechaCreacion": "datetime",
      "rol": "ADMIN | EMPLEADO"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

**POST** `/api/usuarios`
**Crear usuario**
- Requiere: rol ADMIN
- Body:
```json
{
  "email": "string (requerido, email válido, máx 255)",
  "nombreCompleto": "string (requerido, máx 100)",
  "password": "string (requerido, 8–255 chars)"
}
```
- Response `201`:
```json
{
  "usuarioId": 1,
  "email": "string",
  "nombreCompleto": "string",
  "ultimoAcceso": null,
  "fechaCreacion": "datetime",
  "rol": "EMPLEADO"
}
```

---

**PUT** `/api/usuarios`
**Actualizar nombre de usuario**
- Requiere: autenticación. Un usuario solo puede actualizar su propio nombre, salvo que sea ADMIN.
- Body:
```json
{
  "idUsuario": 1,
  "nombreCompleto": "string (requerido, máx 100)"
}
```
- Response `200`: `UsuarioResponse`

---

**DELETE** `/api/usuarios`
**Desactivar usuario**
- Requiere: rol ADMIN
- Body:
```json
{ "idUsuario": 1 }
```
- Response `200`: vacío

---

**PUT** `/api/usuarios/rol`
**Cambiar rol de usuario**
- Requiere: rol ADMIN
- Body:
```json
{
  "idUsuario": 1,
  "rol": "ADMIN | EMPLEADO"
}
```
- Response `200`: `UsuarioResponse`

---

**PUT** `/api/usuarios/password`
**Cambiar contraseña**
- Requiere: autenticación. Un usuario solo puede cambiar su propia contraseña, salvo que sea ADMIN.
- Body:
```json
{
  "idUsuario": 1,
  "password": "string (requerido, 8–255 chars)"
}
```
- Response `200`: `UsuarioResponse`

---

## Parámetros

---

**GET** `/api/parametros`
**Obtener parámetros del negocio**
- Parámetros: ninguno
- Requiere: autenticación
- Response `200`:
```json
{
  "unaCompraDistintosProveedores": true,
  "exigirFormaDePagoAlCargarVenta": false,
  "exigirClienteAlCargarVenta": true,
  "permitirVenderSinStock": false,
  "empleadoPuedeCargarCompras": true,
  "empleadoPuedeUtilizarAbm": false
}
```

---

**PUT** `/api/parametros`
**Actualizar parámetros del negocio**
- Requiere: rol ADMIN
- Body (todos los campos requeridos, boolean):
```json
{
  "unaCompraDistintosProveedores": true,
  "exigirFormaDePagoAlCargarVenta": false,
  "exigirClienteAlCargarVenta": true,
  "permitirVenderSinStock": false,
  "empleadoPuedeCargarCompras": true,
  "empleadoPuedeUtilizarAbm": false
}
```
- Response `200`: misma estructura que el GET

---

## Clientes

---

**GET** `/api/clientes`
**Listar clientes del negocio (paginado)**
- Requiere: autenticación
- Query params:
  - `page` (default: 0)
  - `sortBy` (default: `"fechaCreacion"`)
  - `sortDir` (default: `"asc"`)
- Response `200`:
```json
{
  "content": [
    {
      "idCliente": 1,
      "nombreCompleto": "string",
      "telefono": "string | null"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

**POST** `/api/clientes`
**Crear cliente**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "nombreCompleto": "string (requerido, máx 100)",
  "telefono": "string (opcional, 7–20 chars)"
}
```
- Response `201`:
```json
{
  "idCliente": 1,
  "nombreCompleto": "string",
  "telefono": "string | null"
}
```

---

**PUT** `/api/clientes`
**Actualizar cliente**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "idCliente": 1,
  "nombreCompleto": "string (requerido, máx 100)",
  "telefono": "string (opcional, 7–20 chars)"
}
```
- Response `200`:
```json
{
  "idCliente": 1,
  "nombreCompleto": "string",
  "telefono": "string | null"
}
```

---

**DELETE** `/api/clientes`
**Desactivar cliente (baja lógica)**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{ "idCliente": 1 }
```
- Response `200`: vacío

---

## Proveedores

---

**GET** `/api/proveedores`
**Listar proveedores del negocio (paginado)**
- Requiere: autenticación
- Query params:
  - `page` (default: 0)
  - `sortBy` (default: `"descripcion"`)
  - `sortDir` (default: `"asc"`)
- Response `200`:
```json
{
  "content": [
    {
      "idProveedor": 1,
      "descripcion": "string",
      "telefono": "string | null"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

**POST** `/api/proveedores`
**Crear proveedor**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "descripcion": "string (requerido, máx 100)",
  "telefono": "string (opcional, 7–20 chars)"
}
```
- Response `200`:
```json
{
  "idProveedor": 1,
  "descripcion": "string",
  "telefono": "string | null"
}
```

---

**PUT** `/api/proveedores`
**Actualizar proveedor**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "idProveedor": 1,
  "descripcion": "string (requerido, máx 100)",
  "telefono": "string (opcional, 7–20 chars)"
}
```
- Response `200`:
```json
{
  "idProveedor": 1,
  "descripcion": "string",
  "telefono": "string | null"
}
```

---

**DELETE** `/api/proveedores`
**Desactivar proveedor (baja lógica)**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{ "idProveedor": 1 }
```
- Response `200`: vacío

---

## Categorias

---

**GET** `/api/categorias`
**Listar categorías del negocio (paginado)**
- Requiere: autenticación
- Query params:
  - `page` (default: 0)
  - `sortBy` (default: `"fechaCreacion"`)
  - `sortDir` (default: `"asc"`)
- Response `200`:
```json
{
  "content": [
    {
      "idCategoria": 1,
      "descripcion": "string",
      "fechaCreacion": "datetime",
      "subcategorias": [
        {
          "idSubcategoria": 1,
          "descripcion": "string",
          "fechaCreacion": "datetime"
        }
      ]
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

**POST** `/api/categorias`
**Crear categoría**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "descripcion": "string (requerido, máx 100)"
}
```
- Response `201`:
```json
{
  "idCategoria": 1,
  "descripcion": "string",
  "fechaCreacion": "datetime",
  "subcategorias": []
}
```

---

**PUT** `/api/categorias`
**Actualizar categoría**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "idCategoria": 1,
  "descripcion": "string (requerido, máx 100)"
}
```
- Response `200`: `CategoriaResponse`

---

**DELETE** `/api/categorias`
**Desactivar categoría (baja lógica)**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Efecto en cascada: desactiva todas sus subcategorías y limpia `categoriaId` y `subcategoriaId` de los productos asociados.
- Body:
```json
{ "idCategoria": 1 }
```
- Response `200`: vacío

---

## Subcategorias

---

**POST** `/api/categorias/subcategorias`
**Crear subcategoría**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "categoriaId": 1,
  "descripcion": "string (requerido, máx 100)"
}
```
- Response `201`:
```json
{
  "idSubcategoria": 1,
  "descripcion": "string",
  "fechaCreacion": "datetime"
}
```

---

**PUT** `/api/categorias/subcategorias`
**Actualizar descripción de subcategoría**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Nota: solo se puede modificar la descripción. La categoría a la que pertenece no cambia.
- Body:
```json
{
  "idSubcategoria": 1,
  "descripcion": "string (requerido, máx 100)"
}
```
- Response `200`:
```json
{
  "idSubcategoria": 1,
  "descripcion": "string",
  "fechaCreacion": "datetime"
}
```

---

**DELETE** `/api/categorias/subcategorias`
**Desactivar subcategoría (baja lógica)**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Efecto: limpia `subcategoriaId` de los productos que la referenciaban.
- Body:
```json
{ "idSubcategoria": 1 }
```
- Response `200`: vacío

---

## Productos

---

**GET** `/api/productos`
**Listar productos del negocio (paginado)**
- Requiere: autenticación
- Query params:
  - `busqueda` (opcional) — filtra productos cuya descripción contenga el término (case-insensitive).
  - `categoriaId` (opcional) — filtra por categoría. Usar `0` para obtener solo productos sin categoría asignada.
  - `subcategoriaId` (opcional) — filtra por subcategoría dentro de la categoría indicada; requiere `categoriaId` con un ID real (distinto de `0`); debe pertenecer a la categoría indicada.
  - `proveedorId` (opcional) — filtra por proveedor.
  - `page` (default: 0)
  - Todos los filtros son acumulables entre sí. Orden fijo: `descripcion` ascendente.
- Response `200`:
```json
{
  "content": [
    {
      "productoId": 1,
      "descProducto": "string",
      "stock": 0,
      "precio": 0.00,
      "fechaCreacion": "datetime",
      "categoriaId": 1,
      "descCategoria": "string",
      "subcategoriaId": 1,
      "descSubcategoria": "string",
      "proveedorId": 1,
      "descProveedor": "string"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```
- Nota: `categoriaId`, `subcategoriaId` y `proveedorId` retornan `0` si no están asignados; sus descripciones retornan `""`.
- `precio` es siempre el precio de venta vigente.

---

**POST** `/api/productos`
**Crear producto**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "descripcion": "string (requerido, máx 100, único por negocio)",
  "stock": 0,
  "precio": 0.00,
  "categoriaId": 1,
  "subcategoriaId": 1,
  "proveedorId": 1
}
```
- `stock`: requerido, rango −10.000 a 10.000
- `precio`: requerido, rango −100.000.000 a 100.000.000
- `categoriaId`, `subcategoriaId`, `proveedorId`: opcionales. Si se envía `subcategoriaId`, también debe enviarse `categoriaId` y la subcategoría debe pertenecer a dicha categoría.
- Efecto: crea el producto activo y registra el primer precio de venta.
- Response `200`: `ProductoResponse`

---

**PUT** `/api/productos`
**Actualizar producto**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{
  "idProducto": 1,
  "descripcion": "string (requerido, máx 100)",
  "stock": 0,
  "precio": 0.00,
  "categoriaId": 1,
  "subcategoriaId": 1,
  "proveedorId": 1
}
```
- `categoriaId`, `subcategoriaId`, `proveedorId`: opcionales. Si se envía `subcategoriaId`, también debe enviarse `categoriaId` y la subcategoría debe pertenecer a dicha categoría.
- Si `precio` cambia respecto al precio de venta actual, se cierra el precio vigente y se crea uno nuevo (ver sección Precios).
- Response `200`: `ProductoResponse`

---

**DELETE** `/api/productos`
**Desactivar producto (baja lógica)**
- Requiere: ABM habilitado (rol ADMIN o `empleadoPuedeUtilizarAbm = true`)
- Body:
```json
{ "idProducto": 1 }
```
- Response `200`: vacío

---

## Compras

> **Permiso requerido para escritura:** rol ADMIN o parámetro `empleadoPuedeCargarCompras = true`.

---

**GET** `/api/compras`
**Listar compras del negocio (paginado)**
- Requiere: autenticación
- Query params:
  - `page` (default: 0)
  - `sortBy` (default: `"fechaCreacion"`)
  - `sortDir` (default: `"asc"`)
- Response `200`:
```json
{
  "content": [
    {
      "compraId": 1,
      "fechaCreacion": "datetime",
      "usuarioId": 1,
      "nombreUsuario": "string",
      "recibida": false,
      "fechaRecepcion": "datetime | null",
      "items": [
        {
          "itemId": 1,
          "compraId": 1,
          "productoId": 1,
          "descProducto": "string",
          "precio": 0.00,
          "cantidad": 0
        }
      ]
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

**POST** `/api/compras`
**Crear compra**
- Requiere: permiso compras
- Efecto: crea la compra con los ítems indicados. Al marcarla como recibida se actualiza el stock de cada producto y se registra el precio de compra.
- Body:
```json
{
  "fechaRecepcion": "datetime (opcional)",
  "items": [
    {
      "productoId": 1,
      "cantidad": 0
    }
  ]
}
```
- `items`: requerido. Cada ítem requiere `productoId` y `cantidad` (0–1.000.000).
- Response `201`: `CompraResponse`

---

**PUT** `/api/compras/recibida`
**Marcar compra como recibida**
- Requiere: permiso compras
- Efecto: marca la compra como recibida, actualiza el stock de los productos y registra el precio de compra de cada ítem.
- Body:
```json
{ "idCompra": 1 }
```
- Response `200`: `CompraResponse`

---

**POST** `/api/compras/items`
**Agregar ítem a una compra**
- Requiere: permiso compras
- Body:
```json
{
  "compraId": 1,
  "productoId": 1,
  "cantidad": 0
}
```
- `cantidad`: requerida, rango 0–1.000.000.
- Response `201`: `ItemCompraResponse`

---

**PUT** `/api/compras/items`
**Editar cantidad de un ítem**
- Requiere: permiso compras
- Body:
```json
{
  "compraId": 1,
  "itemCompraId": 1,
  "cantidad": 0
}
```
- `cantidad`: requerida, rango 0–1.000.000.
- Response `200`: `ItemCompraResponse`

---

**DELETE** `/api/compras/items`
**Quitar ítem de una compra**
- Requiere: permiso compras
- Body:
```json
{
  "compraId": 1,
  "itemCompraId": 1
}
```
- Response `200`: vacío

---

**DELETE** `/api/compras`
**Eliminar compra**
- Requiere: permiso compras
- Body:
```json
{ "compraId": 1 }
```
- Response `200`: vacío

---

## Precios

Los precios no tienen endpoints propios. Se gestionan automáticamente al crear o actualizar un producto.

**Modelo de precio (`Precio`):**
```json
{
  "monto": 0.00,
  "tipo": "COMPRA | VENTA",
  "desde": "datetime",
  "hasta": "datetime | null"
}
```

**Comportamiento:**
- El precio activo de un producto es el que tiene `hasta = null`.
- Al asignar un nuevo precio se cierra el anterior (se establece `hasta` con la fecha/hora actual) y se crea un nuevo registro con `desde` = ahora.
- Esto permite consultar el precio histórico vigente en cualquier fecha.
- Al crear un producto se registra automáticamente su primer precio de venta (`tipo = VENTA`).
- Al actualizar un producto, el precio de venta solo se reemplaza si el nuevo valor difiere del actual.
