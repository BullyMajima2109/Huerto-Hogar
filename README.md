# 🌱 Huerto Hogar - Frontend React

## 📋 Descripción del Proyecto

**Huerto Hogar** es una aplicación web de e-commerce desarrollada en React para la venta de productos frescos del campo. El proyecto permite a los usuarios:

- **Explorar productos** de diferentes categorías (frutas, verduras, orgánicos, lácteos)
- **Agregar productos al carrito** de compras
- **Gestionar su carrito** (aumentar/disminuir cantidades, eliminar productos)
- **Realizar compras** creando órdenes en el sistema
- **Autenticarse** con sistema de login y registro
- **Ver sus órdenes** históricas
- **Administrar productos** (solo usuarios con rol ADMIN)

El frontend está completamente integrado con un backend Spring Boot mediante API REST, utilizando autenticación JWT para la seguridad y gestión de sesiones persistente.

## 🚀 Tecnologías Utilizadas

- **React 19.2.0** - Biblioteca principal para la interfaz de usuario
- **React Router DOM 7.9.6** - Enrutamiento y navegación
- **React Bootstrap 2.10.10** - Componentes UI basados en Bootstrap
- **Bootstrap 5.3.8** - Framework CSS para estilos
- **React Scripts 5.0.1** - Herramientas de desarrollo y build

## 📦 Instalación

### Requisitos Previos

- Node.js 16 o superior
- npm o yarn
- Backend Spring Boot corriendo (ver [README del Backend](../huerto-hogar-backend/README.md))

### Pasos de Instalación

1. **Clonar el repositorio**

```bash
git clone https://github.com/Eepy-Dev/huerto-hogar.git
cd huerto-hogar
git checkout frontend
```

2. **Instalar dependencias**

```bash
npm install
```

3. **Configurar variables de entorno**

Crear un archivo `.env` en la raíz del proyecto:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

Si el backend está en otro puerto o servidor, ajustar la URL según corresponda.

4. **Iniciar el servidor de desarrollo**

```bash
npm start
```

La aplicación estará disponible en `http://localhost:3000`

## 🏗️ Estructura del Proyecto

```
huerto-hogar-react/
├── public/
│   ├── img/              # Imágenes de productos
│   ├── js/               # Scripts JavaScript (cartLogic.js para tests)
│   └── index.html        # HTML principal
├── src/
│   ├── components/       # Componentes React
│   │   ├── AdminPanel.jsx      # Panel de administración (solo ADMIN)
│   │   ├── CheckoutPage.jsx    # Página de checkout/boleta
│   │   ├── Login.jsx           # Formulario de login
│   │   ├── ProductCard.jsx     # Tarjeta individual de producto
│   │   ├── ProductList.jsx     # Lista de productos
│   │   ├── ProtectedRoute.jsx  # Componente para rutas protegidas
│   │   ├── Register.jsx        # Formulario de registro
│   │   └── ShoppingCart.jsx    # Carrito de compras
│   ├── services/         # Servicios para comunicación con API
│   │   ├── api.js              # Cliente HTTP y funciones API
│   │   └── authService.js      # Servicio de autenticación
│   ├── data/
│   │   └── catalog.js          # Catálogo local (fallback)
│   ├── App.js            # Componente principal con routing
│   └── index.js          # Punto de entrada
├── tests/                # Tests unitarios
│   └── cartLogic.spec.js
└── package.json
```

## 🎯 Funcionalidades Principales

### 1. Catálogo de Productos

- **Visualización de productos**: Muestra todos los productos disponibles obtenidos desde el backend
- **Categorías**: Productos organizados por categorías (frutas, verduras, orgánicos, lácteos)
- **Información detallada**: Cada producto muestra nombre, unidad, precio e imagen
- **Modo offline**: Si el backend no está disponible, usa el catálogo local como fallback

### 2. Carrito de Compras

- **Agregar productos**: Botón para agregar productos al carrito
- **Gestionar cantidades**: Aumentar o disminuir la cantidad de cada producto
- **Eliminar productos**: Remover productos del carrito
- **Vaciar carrito**: Limpiar todo el carrito de una vez
- **Persistencia**: El carrito se guarda en localStorage y persiste entre sesiones
- **Cálculo de totales**: Muestra el total de la compra en tiempo real

### 3. Autenticación y Sesiones

- **Registro de usuarios**: Formulario para crear nueva cuenta
- **Login**: Inicio de sesión con email y contraseña
- **Gestión de sesiones**: 
  - El token JWT se guarda en localStorage
  - La sesión persiste incluso al recargar la página
  - Validación automática del token en cada petición
  - Redirección automática si el token expira
- **Logout**: Cerrar sesión y limpiar datos de autenticación

### 4. Órdenes de Compra

- **Checkout**: Página para revisar y confirmar la compra
- **Creación de órdenes**: Al confirmar, se crea una orden en el backend
- **Historial de órdenes**: Los usuarios pueden ver todas sus órdenes anteriores
- **Detalles de orden**: Muestra número de boleta, productos, cantidades y total

### 5. Panel de Administración

- **Acceso restringido**: Solo usuarios con rol `ROLE_ADMIN` pueden acceder
- **Gestión de productos**:
  - Ver todos los productos
  - Crear nuevos productos
  - Editar productos existentes
  - Eliminar productos (soft delete)
- **Interfaz intuitiva**: Tabla con todas las operaciones CRUD

### 6. Restricciones de Acceso

- **Rutas protegidas**: Algunas rutas requieren autenticación
- **Roles y permisos**:
  - `ROLE_USER`: Puede ver productos, crear órdenes y ver sus órdenes
  - `ROLE_ADMIN`: Acceso completo, incluyendo panel de administración
- **Redirección automática**: Si un usuario no autenticado intenta acceder a una ruta protegida, se redirige al login

## 🔐 Seguridad

### Autenticación JWT

- **Token almacenado**: El token JWT se guarda en localStorage
- **Headers automáticos**: Todas las peticiones al backend incluyen el token en el header `Authorization: Bearer <token>`
- **Validación de expiración**: Si el token expira (401), se limpia la sesión y redirige al login
- **Protección de rutas**: Componente `ProtectedRoute` valida autenticación y roles antes de renderizar

### Gestión de Sesiones

- **Persistencia**: La sesión persiste en localStorage
- **Recuperación automática**: Al recargar la página, se recupera el usuario y token del localStorage
- **Sincronización**: El estado de autenticación se mantiene sincronizado en toda la aplicación

## 🔌 Integración con Backend

### Endpoints Utilizados

- **Autenticación**:
  - `POST /api/auth/login` - Iniciar sesión
  - `POST /api/auth/register` - Registrar usuario

- **Productos**:
  - `GET /api/productos` - Listar todos los productos
  - `GET /api/productos/{id}` - Obtener producto por ID
  - `GET /api/productos/categoria/{category}` - Productos por categoría
  - `POST /api/productos` - Crear producto (ADMIN)
  - `PUT /api/productos/{id}` - Actualizar producto (ADMIN)
  - `DELETE /api/productos/{id}` - Eliminar producto (ADMIN)

- **Órdenes**:
  - `GET /api/ordenes/mis-ordenes` - Obtener mis órdenes
  - `GET /api/ordenes/{id}` - Obtener orden por ID
  - `POST /api/ordenes` - Crear orden
  - `POST /api/ordenes/{id}/confirmar` - Confirmar orden
  - `POST /api/ordenes/{id}/cancelar` - Cancelar orden

### Manejo de Errores

- **Errores de red**: Muestra mensajes amigables al usuario
- **Errores de autenticación**: Redirige automáticamente al login
- **Fallback offline**: Si el backend no está disponible, usa datos locales

## 🧪 Testing

El proyecto incluye tests unitarios para la lógica del carrito usando Jasmine y Karma:

```bash
npm run test:karma
```

## 📦 Build para Producción

Para crear una versión optimizada para producción:

```bash
npm run build
```

Esto generará una carpeta `build/` con los archivos estáticos listos para desplegar.

## 🚢 Despliegue

### Opciones de Despliegue

1. **Netlify**: Arrastrar la carpeta `build/` o conectar el repositorio
2. **Vercel**: Conectar el repositorio de GitHub
3. **GitHub Pages**: Usar `gh-pages` package
4. **Servidor propio**: Subir la carpeta `build/` a un servidor web

### Variables de Entorno en Producción

Asegúrate de configurar `REACT_APP_API_URL` con la URL del backend en producción.

## 📝 Scripts Disponibles

- `npm start` - Inicia el servidor de desarrollo
- `npm build` - Crea la versión de producción
- `npm test` - Ejecuta tests de React Testing Library
- `npm run test:karma` - Ejecuta tests con Karma/Jasmine

## 👥 Roles de Usuario

### Usuario Normal (ROLE_USER)
- Ver catálogo de productos
- Agregar productos al carrito
- Realizar compras
- Ver sus propias órdenes

### Administrador (ROLE_ADMIN)
- Todas las funcionalidades de usuario normal
- Acceso al panel de administración
- Crear, editar y eliminar productos
- Ver todas las órdenes del sistema

## 🔧 Configuración

### Variables de Entorno

Crear archivo `.env`:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

### Personalización

- **Colores**: Modificar variables CSS en `src/index.css`
- **API Base URL**: Cambiar en `.env` o directamente en `src/services/api.js`
- **Catálogo local**: Editar `src/data/catalog.js`

## 📚 Documentación Adicional

- [Documentación de React](https://react.dev/)
- [React Router](https://reactrouter.com/)
- [React Bootstrap](https://react-bootstrap.github.io/)
- [Backend API Documentation](../huerto-hogar-backend/README.md)

## 🤝 Contribución

Este proyecto fue desarrollado como parte de una evaluación académica. Para contribuir:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es de uso educativo.

## 👨‍💻 Autor

Desarrollado como parte del curso DSY1104 - Desarrollo Fullstack II

---

**Nota**: Este frontend requiere que el backend Spring Boot esté corriendo para funcionar completamente. Asegúrate de seguir las instrucciones del [README del Backend](../huerto-hogar-backend/README.md) para configurar y ejecutar el backend.
