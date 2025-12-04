-- Script de datos iniciales para desarrollo
-- Este script se ejecuta automáticamente si spring.jpa.hibernate.ddl-auto=create o create-drop

-- Insertar productos del catálogo
INSERT INTO productos (producto_id, name, unit, price, img, `desc`, category, activo) VALUES
('manzana-fuji', 'Manzana Fuji', '1 kg', 1200, '/img/manzanafuji.jpg', 'Manzana crujiente y dulce.', 'frutas', true),
('naranja', 'Naranja', '1 kg', 1000, '/img/naranja.jpg', 'Jugosa y fresca.', 'frutas', true),
('frutillas', 'Frutillas', '500 g', 1800, '/img/frutilla.jpg', 'Dulces y aromáticas.', 'frutas', true),
('arandanos', 'Arándanos', '125 g', 1500, '/img/arandanos.jpeg', 'Ricos en antioxidantes.', 'frutas', true),
('frambuesas', 'Frambuesas', '125 g', 1600, '/img/frambuesa.png', 'Sabor intenso.', 'frutas', true),
('mandarinas', 'Mandarinas', '1 kg', 1100, '/img/mandarina.jpg', 'Fáciles de pelar.', 'frutas', true),
('melon', 'Melón', 'Unidad', 2500, '/img/melon.jpg', 'Dulce y refrescante.', 'frutas', true),
('sandia', 'Sandía', 'Unidad', 3500, '/img/sandia.jpg', 'Muy jugosa.', 'frutas', true),
('uvas', 'Uvas', '1 bandeja', 2000, '/img/uva.png', 'Firmes y dulces.', 'frutas', true),
('platano', 'Plátano', '1 kg', 800, '/img/platano.jpg', 'Versátil y rendidor.', 'frutas', true),
('zanahoria', 'Zanahoria', '1 kg', 900, '/img/zanahoria.jpg', 'Dulce y crocante.', 'verduras', true),
('espinaca', 'Espinaca', 'Bolsa 500 g', 700, '/img/espinaca.jpg', 'Hojas tiernas.', 'verduras', true),
('pimientos', 'Pimientos', '1 kg', 1500, '/img/pimientos.jpg', 'Llenos de color.', 'verduras', true),
('miel-organica', 'Miel Orgánica', 'Frasco 500 g', 5000, '/img/miel.jpg', 'Miel pura local.', 'organicos', true),
('quinoa-organica', 'Quinoa Orgánica', '1 kg', 3500, '/img/quinoa.jpg', 'Alto valor proteico.', 'organicos', true),
('leche-entera', 'Leche Entera', '1 litro', 1200, '/img/leche.jpg', 'Ideal para desayunos.', 'lacteos', true);

-- Insertar usuario administrador por defecto (password: admin123)
-- La contraseña debe estar hasheada con BCrypt
-- Para generar: usar BCryptPasswordEncoder o endpoint de registro
-- INSERT INTO usuarios (email, password, nombre, apellido, activo, fecha_creacion) VALUES
-- ('admin@huertohogar.cl', '$2a$10$...', 'Admin', 'Sistema', true, NOW());

