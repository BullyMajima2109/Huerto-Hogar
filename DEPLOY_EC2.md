# 🚀 Guía de Despliegue Frontend en EC2

## Requisitos Previos

- EC2 Ubuntu 22.04 LTS
- IP Elástica asignada (ejemplo: `34.205.67.150`)
- Backend corriendo en otra EC2 (IP: `54.83.138.68:8080`)

## Pasos de Instalación

### 1. Actualizar el sistema

```bash
sudo apt update
sudo apt upgrade -y
```

### 2. Instalar Node.js y npm

```bash
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs
node --version
npm --version
```

### 3. Instalar Nginx

```bash
sudo apt install -y nginx
sudo systemctl start nginx
sudo systemctl enable nginx
```

### 4. Configurar Firewall (Security Groups)

Asegúrate de que el Security Group de tu EC2 tenga estas reglas:

- **HTTP (80)**: `0.0.0.0/0`
- **HTTPS (443)**: `0.0.0.0/0` (opcional)
- **SSH (22)**: Tu IP

### 5. Clonar el repositorio

```bash
cd ~
git clone https://github.com/Eepy-Dev/huerto-hogar.git
cd huerto-hogar
git checkout frontend
cd huerto-hogar-react
```

### 6. Instalar dependencias

```bash
npm install
```

### 7. Configurar variables de entorno

Crear archivo `.env.production`:

```bash
nano .env.production
```

Contenido (reemplaza con la IP Elastic de tu backend):

```env
REACT_APP_API_URL=http://54.83.138.68:8080/api
```

### 8. Compilar el proyecto

```bash
npm run build
```

Esto creará la carpeta `build/` con los archivos estáticos.

### 9. Configurar Nginx

```bash
sudo nano /etc/nginx/sites-available/huerto-hogar-frontend
```

Contenido (reemplaza `34.205.67.150` con tu IP Elastic del frontend):

```nginx
server {
    listen 80;
    server_name 34.205.67.150;

    root /home/ubuntu/huerto-hogar/huerto-hogar-react/build;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # Cache para archivos estáticos
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
```

### 10. Habilitar el sitio

```bash
sudo ln -s /etc/nginx/sites-available/huerto-hogar-frontend /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx
```

### 11. Verificar permisos

```bash
sudo chown -R ubuntu:ubuntu /home/ubuntu/huerto-hogar/huerto-hogar-react/build
```

## Actualizar el Frontend

Cuando hagas cambios y los subas a GitHub:

```bash
cd ~/huerto-hogar
git pull origin frontend
cd huerto-hogar-react
npm run build
sudo systemctl reload nginx
```

## Verificar que funciona

1. Abre tu navegador y ve a: `http://34.205.67.150`
2. Deberías ver la página de Huerto Hogar
3. Abre la consola del navegador (F12) y verifica que no haya errores de CORS

## Solución de Problemas

### Error 502 Bad Gateway
- Verifica que Nginx esté corriendo: `sudo systemctl status nginx`
- Verifica que la carpeta `build/` exista: `ls -la ~/huerto-hogar/huerto-hogar-react/build`

### Error de CORS
- Verifica que el backend tenga configurada la IP del frontend en CORS
- Verifica que `REACT_APP_API_URL` en `.env.production` apunte al backend correcto

### No se ven los productos
- Verifica que el backend esté corriendo: `curl http://54.83.138.68:8080/api/productos`
- Verifica la consola del navegador para ver errores de red

