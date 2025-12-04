# Configuración de Variables de Entorno

## Para Producción (EC2)

Crear archivo `.env.production` en la raíz del proyecto con:

```env
REACT_APP_API_URL=http://54.83.138.68:8080/api
```

## Para Desarrollo Local

Crear archivo `.env` en la raíz del proyecto con:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

## Nota

- Reemplaza `54.83.138.68` con la IP Elastic del backend si cambia
- El frontend usa esta variable para todas las llamadas al API

