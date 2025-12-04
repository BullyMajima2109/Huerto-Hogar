# Huerto Hogar Backend

Backend desarrollado con Spring Boot para la tienda online Huerto Hogar.

## Tecnologías

- **Spring Boot 3.2.0**
- **Spring Security** con JWT
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI** para documentación
- **Maven**

## Requisitos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+

## Configuración

### Base de Datos

1. Crear la base de datos PostgreSQL:
```sql
CREATE DATABASE huerto_hogar_db;
```

2. Configurar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/huerto_hogar_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### Ejecución

1. Clonar el repositorio
2. Ejecutar `mvn clean install`
3. Ejecutar `mvn spring-boot:run`

La aplicación estará disponible en `http://localhost:8080`

## Documentación API

Una vez iniciada la aplicación, acceder a:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión (retorna JWT)

### Productos
- `GET /api/productos` - Listar todos los productos
- `GET /api/productos/{id}` - Obtener producto por ID
- `GET /api/productos/categoria/{category}` - Productos por categoría
- `POST /api/productos` - Crear producto (ADMIN)
- `PUT /api/productos/{id}` - Actualizar producto (ADMIN)
- `DELETE /api/productos/{id}` - Eliminar producto (ADMIN)

### Órdenes
- `GET /api/ordenes` - Listar todas las órdenes (ADMIN)
- `GET /api/ordenes/mis-ordenes` - Mis órdenes (USER/ADMIN)
- `GET /api/ordenes/{id}` - Obtener orden por ID
- `POST /api/ordenes` - Crear orden
- `POST /api/ordenes/{id}/confirmar` - Confirmar orden
- `POST /api/ordenes/{id}/cancelar` - Cancelar orden

## Roles

- **ROLE_USER**: Usuario normal, puede ver productos y crear órdenes
- **ROLE_ADMIN**: Administrador, puede gestionar productos y ver todas las órdenes

## Autenticación JWT

Para acceder a endpoints protegidos, incluir el header:
```
Authorization: Bearer <token>
```

El token se obtiene del endpoint `/api/auth/login`.

## Estructura del Proyecto

```
src/main/java/cl/huerto/huertohogar/
├── config/          # Configuraciones (Security, Swagger, CORS)
├── controller/       # Controladores REST
├── dto/             # Data Transfer Objects
├── model/           # Entidades JPA
├── repository/      # Repositorios JPA
├── security/        # Filtros y componentes de seguridad
└── service/         # Lógica de negocio
```

## Despliegue en EC2

Para producción en EC2 con Ubuntu:

1. Configurar variables de entorno o usar `application-prod.properties`
2. Asegurar que PostgreSQL esté configurado correctamente
3. Configurar firewall para permitir puerto 8080
4. Usar un proceso manager como systemd o PM2

