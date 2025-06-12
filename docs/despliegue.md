# Guía de Despliegue

## Preparación del Entorno

### 1. Requisitos del Servidor
- Sistema operativo: Ubuntu 20.04 LTS o superior
- CPU: 2 cores mínimo
- RAM: 4GB mínimo
- Disco: 20GB mínimo
- Java 17 o superior
- MySQL 8.0 o superior
- Nginx (opcional, para proxy inverso)

### 2. Configuración del Sistema
```bash
# Actualizar el sistema
sudo apt update
sudo apt upgrade

# Instalar Java
sudo apt install openjdk-17-jdk

# Instalar MySQL
sudo apt install mysql-server

# Instalar Nginx (opcional)
sudo apt install nginx
```

## Configuración de la Base de Datos

### 1. Crear Base de Datos
```sql
CREATE DATABASE sistema_gestion;
CREATE USER 'sistema_user'@'localhost' IDENTIFIED BY 'contraseña_segura';
GRANT ALL PRIVILEGES ON sistema_gestion.* TO 'sistema_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Importar Esquema
```bash
mysql -u sistema_user -p sistema_gestion < sistema_gestion.sql
```

## Construcción de la Aplicación

### 1. Clonar Repositorio
```bash
git clone https://github.com/Jean-ZR/entregable-1.git
cd entregable-1
```

### 2. Compilar
```bash
./gradlew clean build
```

### 3. Verificar el JAR
```bash
ls -l build/libs/sistema-gestion.jar
```

## Configuración de la Aplicación

### 1. Crear Directorio de Aplicación
```bash
sudo mkdir /opt/sistema-gestion
sudo chown $USER:$USER /opt/sistema-gestion
```

### 2. Copiar Archivos
```bash
cp build/libs/sistema-gestion.jar /opt/sistema-gestion/
cp src/main/resources/application-prod.properties /opt/sistema-gestion/
```

### 3. Configurar Variables de Entorno
```bash
# Crear archivo de variables de entorno
cat > /opt/sistema-gestion/.env << EOL
SPRING_PROFILES_ACTIVE=prod
DB_HOST=localhost
DB_PORT=3306
DB_NAME=sistema_gestion
DB_USER=sistema_user
DB_PASS=contraseña_segura
JWT_SECRET=clave_secreta_muy_larga_y_segura
EOL
```

## Configuración del Servicio

### 1. Crear Servicio Systemd
```bash
sudo nano /etc/systemd/system/sistema-gestion.service
```

```ini
[Unit]
Description=Sistema de Gestión
After=network.target

[Service]
User=sistema
WorkingDirectory=/opt/sistema-gestion
ExecStart=/usr/bin/java -jar sistema-gestion.jar
SuccessExitStatus=143
Restart=always
RestartSec=5
EnvironmentFile=/opt/sistema-gestion/.env

[Install]
WantedBy=multi-user.target
```

### 2. Iniciar Servicio
```bash
sudo systemctl daemon-reload
sudo systemctl enable sistema-gestion
sudo systemctl start sistema-gestion
```

## Configuración de Nginx (Opcional)

### 1. Configurar Sitio
```bash
sudo nano /etc/nginx/sites-available/sistema-gestion
```

```nginx
server {
    listen 80;
    server_name tu-dominio.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### 2. Activar Sitio
```bash
sudo ln -s /etc/nginx/sites-available/sistema-gestion /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

## Configuración SSL (Opcional)

### 1. Instalar Certbot
```bash
sudo apt install certbot python3-certbot-nginx
```

### 2. Obtener Certificado
```bash
sudo certbot --nginx -d tu-dominio.com
```

## Monitoreo

### 1. Configurar Logs
```bash
sudo mkdir /var/log/sistema-gestion
sudo chown sistema:sistema /var/log/sistema-gestion
```

### 2. Configurar Rotación de Logs
```bash
sudo nano /etc/logrotate.d/sistema-gestion
```

```
/var/log/sistema-gestion/*.log {
    daily
    rotate 14
    compress
    delaycompress
    notifempty
    create 0640 sistema sistema
    sharedscripts
    postrotate
        systemctl reload sistema-gestion
    endscript
}
```

## Mantenimiento

### 1. Actualización de la Aplicación
```bash
# Detener el servicio
sudo systemctl stop sistema-gestion

# Hacer backup
cp /opt/sistema-gestion/sistema-gestion.jar /opt/sistema-gestion/backup/sistema-gestion-$(date +%Y%m%d).jar

# Actualizar el JAR
cp build/libs/sistema-gestion.jar /opt/sistema-gestion/

# Reiniciar el servicio
sudo systemctl start sistema-gestion
```

### 2. Backup de Base de Datos
```bash
# Crear script de backup
cat > /opt/sistema-gestion/backup-db.sh << EOL
#!/bin/bash
BACKUP_DIR="/opt/sistema-gestion/backup/db"
DATE=\$(date +%Y%m%d)
mysqldump -u sistema_user -p sistema_gestion > \$BACKUP_DIR/sistema_gestion_\$DATE.sql
find \$BACKUP_DIR -type f -mtime +7 -delete
EOL

# Hacer ejecutable
chmod +x /opt/sistema-gestion/backup-db.sh

# Agregar a crontab
(crontab -l 2>/dev/null; echo "0 2 * * * /opt/sistema-gestion/backup-db.sh") | crontab -
```

## Solución de Problemas

### 1. Verificar Logs
```bash
# Logs de la aplicación
tail -f /var/log/sistema-gestion/application.log

# Logs del sistema
journalctl -u sistema-gestion -f
```

### 2. Verificar Estado del Servicio
```bash
sudo systemctl status sistema-gestion
```

### 3. Verificar Conexión a Base de Datos
```bash
mysql -u sistema_user -p -e "SELECT 1;"
```

### 4. Verificar Puertos
```bash
sudo netstat -tulpn | grep java
```

## Escalabilidad

### 1. Configuración de JVM
```bash
# Modificar el servicio para incluir parámetros de JVM
ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar sistema-gestion.jar
```

### 2. Configuración de Base de Datos
```properties
# application-prod.properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

## Seguridad

### 1. Firewall
```bash
# Configurar UFW
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw enable
```

### 2. Actualizaciones de Seguridad
```bash
# Configurar actualizaciones automáticas
sudo apt install unattended-upgrades
sudo dpkg-reconfigure -plow unattended-upgrades
``` 