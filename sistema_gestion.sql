-- Base de datos: sistema_empleados
CREATE DATABASE IF NOT EXISTS sistema_empleados;
USE sistema_empleados;

select*from roles;

-- Tabla de roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Para bcrypt
    email VARCHAR(100) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_login TIMESTAMP NULL,
    rol_id INT NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Tabla de empleados
CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    fecha_nacimiento DATE,
    fecha_ingreso DATE NOT NULL,
    salario DECIMAL(10,2),
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    usuario_id INT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla de sesiones (para control de login)
CREATE TABLE sesiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion TIMESTAMP NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    ip_address VARCHAR(45),
    user_agent TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Índices para optimización
CREATE INDEX idx_empleados_dni ON empleados(dni);
CREATE INDEX idx_empleados_email ON empleados(email);
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_sesiones_token ON sesiones(token);
CREATE INDEX idx_sesiones_usuario ON sesiones(usuario_id);

-- Datos iniciales
INSERT INTO roles (nombre, descripcion) VALUES 
('ADMINISTRADOR', 'Acceso completo al sistema'),
('EMPLEADO', 'Acceso limitado a consultas');

-- Usuario administrador por defecto (password: admin123)
INSERT INTO usuarios (username, password, email, rol_id) VALUES 
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.Iy.jL3pKoLaHIDnQeZoY4nKdJLUQm6', 'admin@empresa.com', 1);

-- Empleado de ejemplo
INSERT INTO empleados (nombres, apellidos, dni, email, telefono, direccion, fecha_nacimiento, fecha_ingreso, salario, usuario_id) VALUES 
('Juan Carlos', 'Pérez López', '12345678', 'juan.perez@empresa.com', '987654321', 'Av. Lima 123', '1990-05-15', '2023-01-15', 3500.00, 1);

ALTER TABLE sesiones ADD COLUMN fecha_fin TIMESTAMP NULL;
-- Fix the data type mismatches

select*from roles;

SELECT * FROM usuarios WHERE username = 'admin';

UPDATE usuarios
SET password = '$2a$10$VhF7uUO8JkK0Mq4y1nD2SO/mzF7lQYk5kFkl0LhUw1lyuRbBkeVn2'
WHERE username = 'admin';

UPDATE usuarios
SET password = 'nueva_contrasena_hash'
WHERE id = 1; -- O WHERE username = 'nombre_de_usuario'; O WHERE email = 'correo@ejemplo.com';

UPDATE usuarios 
SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMye.Iy.jL3pKoLaHIDnQeZoY4nKdJLUQm6' 
WHERE username = 'admin';

DELETE FROM usuarios WHERE username = 'admin';
