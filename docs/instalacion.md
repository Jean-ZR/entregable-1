# Guía de Instalación

## Preparación del Entorno

### 1. Instalación de Java
```bash
# Verificar la versión de Java
java -version

# Si no está instalado, descargar e instalar JDK 17 desde:
# https://adoptium.net/
```

### 2. Instalación de MySQL
```bash
# Para Windows:
# Descargar MySQL Installer desde:
# https://dev.mysql.com/downloads/installer/

# Para Linux (Ubuntu):
sudo apt update
sudo apt install mysql-server
```

### 3. Configuración de la Base de Datos
```sql
# Crear la base de datos
CREATE DATABASE sistema_gestion;

# Importar el esquema
mysql -u root -p sistema_gestion < sistema_gestion.sql
```

## Instalación del Proyecto

### 1. Clonar el Repositorio
```bash
git clone https://github.com/Jean-ZR/entregable-1.git
cd entregable-1
```

### 2. Configuración del Proyecto
1. Abrir el archivo `src/main/resources/application.properties`
2. Configurar las credenciales de la base de datos:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_gestion
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Compilación del Proyecto
```bash
# Usando Maven
mvn clean install

# Usando Gradle
./gradlew build
```

### 4. Ejecución de la Aplicación
```bash
# Usando Maven
mvn spring-boot:run

# Usando Gradle
./gradlew bootRun
```

## Verificación de la Instalación

1. Abrir el navegador y acceder a:
   - http://localhost:9898

2. Verificar los logs de la aplicación para asegurar que no hay errores

## Solución de Problemas Comunes

### Error de Conexión a la Base de Datos
- Verificar que MySQL está en ejecución
- Confirmar las credenciales en application.properties
- Asegurar que la base de datos existe

### Error de Puerto en Uso
- Verificar que el puerto 9898 está disponible
- Cambiar el puerto en application.properties si es necesario

### Error de Compilación
- Verificar la versión de Java
- Limpiar la caché de Maven/Gradle
- Actualizar las dependencias

## Actualización del Sistema

```bash
# Obtener los últimos cambios
git pull origin main

# Recompilar el proyecto
mvn clean install

# Reiniciar la aplicación
mvn spring-boot:run
``` 