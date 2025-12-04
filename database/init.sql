-- Script de inicialización de base de datos para PostgreSQL
-- Ejecutar este script antes de iniciar la aplicación

CREATE DATABASE huerto_hogar_db;

-- Las tablas se crearán automáticamente por JPA/Hibernate
-- con la configuración spring.jpa.hibernate.ddl-auto=update

-- Opcional: Crear usuario específico para la aplicación
-- CREATE USER huerto_user WITH PASSWORD 'huerto_password';
-- GRANT ALL PRIVILEGES ON DATABASE huerto_hogar_db TO huerto_user;

