# Documentación de la Base de Datos

## Esquema de la Base de Datos

### Tablas Principales

#### Usuarios
```sql
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### Productos
```sql
CREATE TABLE productos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    categoria_id BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);
```

#### Categorías (futura integracion empleados)
```sql
CREATE TABLE categorias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Pedidos
```sql
CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

#### Detalles de Pedido (futura integracion empleados)
```sql
CREATE TABLE detalles_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);
```

## Relaciones

### Diagrama de Relaciones
```
usuarios 1---* pedidos
pedidos 1---* detalles_pedido
productos 1---* detalles_pedido
categorias 1---* productos
```

## Índices

### Índices Principales
- `usuarios(email)`: Índice único para búsqueda por email
- `productos(nombre)`: Índice para búsqueda por nombre
- `pedidos(usuario_id)`: Índice para búsqueda de pedidos por usuario
- `detalles_pedido(pedido_id)`: Índice para búsqueda de detalles por pedido

## Procedimientos Almacenados

### Obtener Pedidos por Usuario
```sql
DELIMITER //
CREATE PROCEDURE sp_obtener_pedidos_usuario(IN p_usuario_id BIGINT)
BEGIN
    SELECT p.*, dp.cantidad, pr.nombre as producto_nombre
    FROM pedidos p
    JOIN detalles_pedido dp ON p.id = dp.pedido_id
    JOIN productos pr ON dp.producto_id = pr.id
    WHERE p.usuario_id = p_usuario_id;
END //
DELIMITER ;
```

## Vistas

### Vista de Productos con Categorías
```sql
CREATE VIEW v_productos_categorias AS
SELECT p.*, c.nombre as categoria_nombre
FROM productos p
LEFT JOIN categorias c ON p.categoria_id = c.id;
```

## Mantenimiento

### Backup
```sql
mysqldump -u root -p sistema_gestion > backup_$(date +%Y%m%d).sql
```

### Restauración
```sql
mysql -u root -p sistema_gestion < backup_YYYYMMDD.sql
```

## Optimización

### Recomendaciones
1. Mantener índices actualizados
2. Realizar backups regulares
3. Monitorear el rendimiento de las consultas
4. Limpiar datos históricos periódicamente
5. Mantener estadísticas actualizadas

## Seguridad

### Buenas Prácticas
1. Usar contraseñas fuertes
2. Limitar accesos por IP
3. Mantener actualizado el servidor MySQL
4. Realizar auditorías periódicas
5. Implementar backup automático 