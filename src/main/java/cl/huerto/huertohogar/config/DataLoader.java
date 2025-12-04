package cl.huerto.huertohogar.config;

import cl.huerto.huertohogar.model.Producto;
import cl.huerto.huertohogar.model.Usuario;
import cl.huerto.huertohogar.repository.ProductoRepository;
import cl.huerto.huertohogar.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        try {
            logger.info("Iniciando carga de datos iniciales...");
            
            // Solo cargar datos si no existen
            long productoCount = productoRepository.count();
            logger.info("Productos existentes: {}", productoCount);
            
            if (productoCount == 0) {
                logger.info("Cargando productos iniciales...");
                cargarProductos();
                logger.info("Productos cargados exitosamente");
            } else {
                logger.info("Ya existen productos, omitiendo carga");
            }
            
            // Crear usuario admin si no existe
            boolean adminExists = usuarioRepository.existsByEmail("admin@huertohogar.cl");
            logger.info("Usuario admin existe: {}", adminExists);
            
            if (!adminExists) {
                logger.info("Creando usuario admin...");
                crearUsuarioAdmin();
                logger.info("Usuario admin creado exitosamente");
            } else {
                logger.info("Usuario admin ya existe, omitiendo creación");
            }
            
            logger.info("Carga de datos iniciales completada");
        } catch (Exception e) {
            logger.error("Error al cargar datos iniciales: {}", e.getMessage(), e);
            // No lanzar la excepción para que la aplicación pueda iniciar
        }
    }
    
    private void cargarProductos() {
        try {
            Producto[] productos = {
                new Producto(null, "manzana-fuji", "Manzana Fuji", "1 kg", 1200, "/img/manzanafuji.jpg", "Manzana crujiente y dulce.", "frutas", true),
                new Producto(null, "naranja", "Naranja", "1 kg", 1000, "/img/naranja.jpg", "Jugosa y fresca.", "frutas", true),
                new Producto(null, "frutillas", "Frutillas", "500 g", 1800, "/img/frutilla.jpg", "Dulces y aromáticas.", "frutas", true),
                new Producto(null, "arandanos", "Arándanos", "125 g", 1500, "/img/arandanos.jpeg", "Ricos en antioxidantes.", "frutas", true),
                new Producto(null, "frambuesas", "Frambuesas", "125 g", 1600, "/img/frambuesa.png", "Sabor intenso.", "frutas", true),
                new Producto(null, "mandarinas", "Mandarinas", "1 kg", 1100, "/img/mandarina.jpg", "Fáciles de pelar.", "frutas", true),
                new Producto(null, "melon", "Melón", "Unidad", 2500, "/img/melon.jpg", "Dulce y refrescante.", "frutas", true),
                new Producto(null, "sandia", "Sandía", "Unidad", 3500, "/img/sandia.jpg", "Muy jugosa.", "frutas", true),
                new Producto(null, "uvas", "Uvas", "1 bandeja", 2000, "/img/uva.png", "Firmes y dulces.", "frutas", true),
                new Producto(null, "platano", "Plátano", "1 kg", 800, "/img/platano.jpg", "Versátil y rendidor.", "frutas", true),
                new Producto(null, "zanahoria", "Zanahoria", "1 kg", 900, "/img/zanahoria.jpg", "Dulce y crocante.", "verduras", true),
                new Producto(null, "espinaca", "Espinaca", "Bolsa 500 g", 700, "/img/espinaca.jpg", "Hojas tiernas.", "verduras", true),
                new Producto(null, "pimientos", "Pimientos", "1 kg", 1500, "/img/pimientos.jpg", "Llenos de color.", "verduras", true),
                new Producto(null, "miel-organica", "Miel Orgánica", "Frasco 500 g", 5000, "/img/miel.jpg", "Miel pura local.", "organicos", true),
                new Producto(null, "quinoa-organica", "Quinoa Orgánica", "1 kg", 3500, "/img/quinoa.jpg", "Alto valor proteico.", "organicos", true),
                new Producto(null, "leche-entera", "Leche Entera", "1 litro", 1200, "/img/leche.jpg", "Ideal para desayunos.", "lacteos", true)
            };
            
            productoRepository.saveAll(Arrays.asList(productos));
        } catch (Exception e) {
            logger.error("Error al cargar productos: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    private void crearUsuarioAdmin() {
        try {
            Usuario admin = new Usuario();
            admin.setEmail("admin@huertohogar.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Admin");
            admin.setApellido("Sistema");
            admin.getRoles().add(Usuario.Rol.ROLE_ADMIN);
            admin.getRoles().add(Usuario.Rol.ROLE_USER);
            admin.setActivo(true);
            
            usuarioRepository.save(admin);
        } catch (Exception e) {
            logger.error("Error al crear usuario admin: {}", e.getMessage(), e);
            throw e;
        }
    }
}

