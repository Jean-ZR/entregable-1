# Documentación de la API

## Información General

- **Base URL**: `http://localhost:9898/api`
- **Formato**: JSON
- **Autenticación**: JWT Bearer Token

## Autenticación

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "usuario@ejemplo.com",
    "password": "contraseña"
}
```

### Respuesta
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tipo": "Bearer",
    "usuario": {
        "id": 1,
        "nombre": "Usuario Ejemplo",
        "email": "usuario@ejemplo.com",
        "rol": "ADMIN"
    }
}
```

## Usuarios

### Obtener Todos los Usuarios
```http
GET /api/usuarios
Authorization: Bearer {token}
```

### Obtener Usuario por ID
```http
GET /api/usuarios/{id}
Authorization: Bearer {token}
```

### Crear Usuario
```http
POST /api/usuarios
Authorization: Bearer {token}
Content-Type: application/json

{
    "nombre": "Nuevo Usuario",
    "email": "nuevo@ejemplo.com",
    "password": "contraseña",
    "rol": "USER"
}
```

### Actualizar Usuario
```http
PUT /api/usuarios/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
    "nombre": "Usuario Actualizado",
    "email": "actualizado@ejemplo.com"
}
```

### Eliminar Usuario
```http
DELETE /api/usuarios/{id}
Authorization: Bearer {token}
```

## Productos

### Obtener Todos los Productos
```http
GET /api/productos
Authorization: Bearer {token}
```

### Obtener Producto por ID
```http
GET /api/productos/{id}
Authorization: Bearer {token}
```

### Crear Producto
```http
POST /api/productos
Authorization: Bearer {token}
Content-Type: application/json

{
    "nombre": "Nuevo Producto",
    "descripcion": "Descripción del producto",
    "precio": 99.99,
    "stock": 100,
    "categoria_id": 1
}
```

### Actualizar Producto
```http
PUT /api/productos/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
    "nombre": "Producto Actualizado",
    "precio": 89.99,
    "stock": 50
}
```

### Eliminar Producto
```http
DELETE /api/productos/{id}
Authorization: Bearer {token}
```

## Pedidos

### Obtener Todos los Pedidos
```http
GET /api/pedidos
Authorization: Bearer {token}
```

### Obtener Pedido por ID
```http
GET /api/pedidos/{id}
Authorization: Bearer {token}
```

### Crear Pedido
```http
POST /api/pedidos
Authorization: Bearer {token}
Content-Type: application/json

{
    "usuario_id": 1,
    "productos": [
        {
            "producto_id": 1,
            "cantidad": 2
        },
        {
            "producto_id": 2,
            "cantidad": 1
        }
    ]
}
```

### Actualizar Estado del Pedido
```http
PUT /api/pedidos/{id}/estado
Authorization: Bearer {token}
Content-Type: application/json

{
    "estado": "ENTREGADO"
}
```

## Categorías

### Obtener Todas las Categorías
```http
GET /api/categorias
Authorization: Bearer {token}
```

### Obtener Categoría por ID
```http
GET /api/categorias/{id}
Authorization: Bearer {token}
```

### Crear Categoría
```http
POST /api/categorias
Authorization: Bearer {token}
Content-Type: application/json

{
    "nombre": "Nueva Categoría",
    "descripcion": "Descripción de la categoría"
}
```

## Códigos de Estado

- `200 OK`: Petición exitosa
- `201 Created`: Recurso creado exitosamente
- `400 Bad Request`: Error en la petición
- `401 Unauthorized`: No autenticado
- `403 Forbidden`: No autorizado
- `404 Not Found`: Recurso no encontrado
- `500 Internal Server Error`: Error del servidor

## Ejemplos de Uso

### Ejemplo de Creación de Pedido
```bash
curl -X POST http://localhost:9898/api/pedidos \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "usuario_id": 1,
    "productos": [
      {
        "producto_id": 1,
        "cantidad": 2
      }
    ]
  }'
```

### Ejemplo de Actualización de Producto
```bash
curl -X PUT http://localhost:9898/api/productos/1 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Producto Actualizado",
    "precio": 89.99
  }'
``` 