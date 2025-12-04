package cl.huerto.huertohogar.service;

import cl.huerto.huertohogar.model.Orden;
import cl.huerto.huertohogar.model.OrdenItem;
import cl.huerto.huertohogar.model.Producto;
import cl.huerto.huertohogar.model.Usuario;
import cl.huerto.huertohogar.repository.OrdenRepository;
import cl.huerto.huertohogar.repository.ProductoRepository;
import cl.huerto.huertohogar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdenService {
    
    @Autowired
    private OrdenRepository ordenRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }
    
    public List<Orden> findByUsuarioId(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }
    
    public Optional<Orden> findById(Long id) {
        return ordenRepository.findById(id);
    }
    
    public Optional<Orden> findByNumeroOrden(String numeroOrden) {
        return ordenRepository.findByNumeroOrden(numeroOrden);
    }
    
    public Orden createOrden(Long usuarioId, List<OrdenItemData> itemsData) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        
        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setEstado(Orden.EstadoOrden.PENDIENTE);
        orden.setFechaCreacion(LocalDateTime.now());
        
        int total = 0;
        
        for (OrdenItemData itemData : itemsData) {
            if (itemData.getCantidad() == null || itemData.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor o igual a 1");
            }
            
            Producto producto = null;
            if (itemData.getProductoId() != null) {
                producto = productoRepository.findById(itemData.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemData.getProductoId()));
            } else if (StringUtils.hasText(itemData.getProductoCodigo())) {
                producto = productoRepository.findByProductoId(itemData.getProductoCodigo())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + itemData.getProductoCodigo()));
            } else {
                throw new RuntimeException("Cada item debe incluir un productoId o productoCodigo");
            }
            
            if (!producto.getActivo()) {
                throw new RuntimeException("Producto inactivo: " + producto.getName());
            }
            
            OrdenItem item = new OrdenItem();
            item.setOrden(orden);
            item.setProducto(producto);
            item.setCantidad(itemData.getCantidad());
            item.setPrecioUnitario(producto.getPrice());
            item.setSubtotal(producto.getPrice() * itemData.getCantidad());
            
            orden.getItems().add(item);
            total += item.getSubtotal();
        }
        
        orden.setTotal(total);
        
        // Generar número de orden único
        String numeroOrden = "HH-" + System.currentTimeMillis();
        while (ordenRepository.existsByNumeroOrden(numeroOrden)) {
            numeroOrden = "HH-" + System.currentTimeMillis();
        }
        orden.setNumeroOrden(numeroOrden);
        
        return ordenRepository.save(orden);
    }
    
    public Orden confirmarOrden(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        
        if (orden.getEstado() != Orden.EstadoOrden.PENDIENTE) {
            throw new RuntimeException("Solo se pueden confirmar órdenes pendientes");
        }
        
        orden.setEstado(Orden.EstadoOrden.CONFIRMADA);
        orden.setFechaActualizacion(LocalDateTime.now());
        
        return ordenRepository.save(orden);
    }
    
    public Orden cancelarOrden(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        
        if (orden.getEstado() == Orden.EstadoOrden.CONFIRMADA) {
            throw new RuntimeException("No se pueden cancelar órdenes confirmadas");
        }
        
        orden.setEstado(Orden.EstadoOrden.CANCELADA);
        orden.setFechaActualizacion(LocalDateTime.now());
        
        return ordenRepository.save(orden);
    }
    
    // Clase auxiliar para recibir datos de items
    public static class OrdenItemData {
        private Long productoId;
        private String productoCodigo;
        private Integer cantidad;
        
        public Long getProductoId() {
            return productoId;
        }
        
        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }
        
        public String getProductoCodigo() {
            return productoCodigo;
        }
        
        public void setProductoCodigo(String productoCodigo) {
            this.productoCodigo = productoCodigo;
        }
        
        public Integer getCantidad() {
            return cantidad;
        }
        
        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}

