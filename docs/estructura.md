# Estructura del Proyecto

## Estructura de Directorios

```
entregable-1/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── sistema/
│   │   │           ├── controllers/
│   │   │           ├── models/
│   │   │           ├── repositories/
│   │   │           ├── services/
│   │   │           └── SistemaApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
│       └── java/
├── docs/
├── gradle/
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

## Descripción de Componentes

### Directorio `src/main/java/com/sistema/`
- **controllers/**: Controladores REST que manejan las peticiones HTTP
- **models/**: Entidades y DTOs que representan la estructura de datos
- **repositories/**: Interfaces para el acceso a datos
- **services/**: Lógica de negocio de la aplicación
- **SistemaApplication.java**: Punto de entrada de la aplicación

### Directorio `src/main/resources/`
- **static/**: Archivos estáticos (CSS, JavaScript, imágenes)
- **templates/**: Plantillas para la interfaz de usuario
- **application.properties**: Configuración de la aplicación

### Directorio `src/test/`
- Contiene las pruebas unitarias y de integración

### Archivos de Configuración
- **build.gradle**: Configuración de dependencias y build
- **application.properties**: Configuración de la aplicación
- **.gitignore**: Archivos y directorios ignorados por Git

## Convenciones de Nomenclatura

### Clases Java
- **Controllers**: `*Controller.java`
- **Models**: `*Model.java` o `*Entity.java`
- **Repositories**: `*Repository.java`
- **Services**: `*Service.java`

### Archivos de Recursos
- **Templates**: `*.html`
- **Estilos**: `*.css`
- **Scripts**: `*.js`

## Patrones de Diseño Utilizados

- **MVC (Model-View-Controller)**
- **Repository Pattern**
- **Service Layer Pattern**
- **DTO Pattern**

## Flujo de Datos

1. Las peticiones HTTP llegan a los controladores
2. Los controladores utilizan los servicios
3. Los servicios interactúan con los repositorios
4. Los repositorios acceden a la base de datos
5. Los datos fluyen de vuelta a través de las capas
6. La respuesta se envía al cliente

## Buenas Prácticas

- Separación clara de responsabilidades
- Código limpio y mantenible
- Documentación en el código
- Pruebas unitarias
- Manejo de excepciones
- Logging apropiado 