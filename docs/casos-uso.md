# Casos de Uso

## 1. Gestión de Usuarios

### 1.1 Registro de Usuario
**Actor**: Usuario no registrado
**Precondiciones**: 
- El usuario no tiene una cuenta en el sistema
- El correo electrónico no está registrado

**Flujo Principal**:
1. El usuario accede a la página de registro
2. Completa el formulario con sus datos
3. El sistema valida los datos
4. Se crea la cuenta
5. Se envía correo de confirmación

**Postcondiciones**:
- Se crea una nueva cuenta de usuario
- El usuario puede iniciar sesión

### 1.2 Inicio de Sesión
**Actor**: Usuario registrado
**Precondiciones**: 
- El usuario tiene una cuenta activa

**Flujo Principal**:
1. El usuario accede a la página de login
2. Ingresa sus credenciales
3. El sistema valida las credenciales
4. Se genera el token JWT
5. Se redirige al dashboard

**Postcondiciones**:
- El usuario está autenticado
- Se genera una sesión activa

## 2. Gestión de Productos

### 2.1 Creación de Producto
**Actor**: Administrador
**Precondiciones**:
- El administrador está autenticado
- Tiene permisos de administrador

**Flujo Principal**:
1. El administrador accede al panel de productos
2. Selecciona "Crear Producto"
3. Completa el formulario de producto
4. El sistema valida los datos
5. Se crea el producto

**Postcondiciones**:
- Se crea un nuevo producto en el catálogo
- El producto está disponible para venta

### 2.2 Actualización de Stock
**Actor**: Administrador
**Precondiciones**:
- El producto existe en el sistema
- El administrador está autenticado

**Flujo Principal**:
1. El administrador accede al panel de productos
2. Selecciona el producto a actualizar
3. Modifica la cantidad de stock
4. El sistema actualiza el inventario
5. Se registra la modificación

**Postcondiciones**:
- Se actualiza el stock del producto
- Se registra la transacción

## 3. Gestión de Pedidos

### 3.1 Creación de Pedido
**Actor**: Usuario autenticado
**Precondiciones**:
- El usuario está autenticado
- Hay productos disponibles

**Flujo Principal**:
1. El usuario selecciona productos
2. Agrega productos al carrito
3. Procede al checkout
4. Confirma el pedido
5. El sistema procesa el pedido

**Postcondiciones**:
- Se crea un nuevo pedido
- Se actualiza el inventario
- Se genera la factura

### 3.2 Seguimiento de Pedido
**Actor**: Usuario autenticado
**Precondiciones**:
- El usuario tiene pedidos activos

**Flujo Principal**:
1. El usuario accede a sus pedidos
2. Selecciona el pedido a consultar
3. El sistema muestra el estado
4. Se visualizan los detalles

**Postcondiciones**:
- El usuario conoce el estado de su pedido

## 4. Gestión de Categorías

### 4.1 Creación de Categoría
**Actor**: Administrador
**Precondiciones**:
- El administrador está autenticado
- Tiene permisos de administrador

**Flujo Principal**:
1. El administrador accede al panel de categorías
2. Selecciona "Crear Categoría"
3. Completa el formulario
4. El sistema valida los datos
5. Se crea la categoría

**Postcondiciones**:
- Se crea una nueva categoría
- La categoría está disponible para productos

## 5. Reportes

### 5.1 Generación de Reporte de Ventas
**Actor**: Administrador
**Precondiciones**:
- El administrador está autenticado
- Hay ventas registradas

**Flujo Principal**:
1. El administrador accede al panel de reportes
2. Selecciona el tipo de reporte
3. Define el período
4. El sistema genera el reporte
5. Se muestra/descarga el reporte

**Postcondiciones**:
- Se genera el reporte solicitado
- El reporte está disponible para descarga

## 6. Manejo de Errores

### 6.1 Error de Autenticación
**Actor**: Usuario
**Precondiciones**:
- El usuario intenta acceder al sistema

**Flujo Principal**:
1. El usuario ingresa credenciales incorrectas
2. El sistema detecta el error
3. Muestra mensaje de error
4. Ofrece opciones de recuperación

**Postcondiciones**:
- Se muestra mensaje de error apropiado
- El usuario puede intentar nuevamente

### 6.2 Error de Stock
**Actor**: Sistema
**Precondiciones**:
- Usuario intenta comprar producto

**Flujo Principal**:
1. El sistema verifica el stock
2. Detecta stock insuficiente
3. Notifica al usuario
4. Ofrece alternativas

**Postcondiciones**:
- Se notifica al usuario
- Se registra el intento de compra 