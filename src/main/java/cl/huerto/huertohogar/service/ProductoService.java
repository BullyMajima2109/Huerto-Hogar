package cl.huerto.huertohogar.service;

import cl.huerto.huertohogar.model.Producto;
import cl.huerto.huertohogar.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> findAll() {
        return productoRepository.findByActivoTrue();
    }
    
    public List<Producto> findByCategory(String category) {
        return productoRepository.findByCategoryAndActivoTrue(category);
    }
    
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id)
                .filter(Producto::getActivo);
    }
    
    public Optional<Producto> findByProductoId(String productoId) {
        return productoRepository.findByProductoId(productoId)
                .filter(Producto::getActivo);
    }
    
    public Producto save(Producto producto) {
        if (producto.getProductoId() == null || producto.getProductoId().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto es obligatorio");
        }
        if (productoRepository.existsByProductoId(producto.getProductoId())) {
            throw new IllegalArgumentException("Ya existe un producto con el ID: " + producto.getProductoId());
        }
        return productoRepository.save(producto);
    }
    
    public Producto update(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        if (!producto.getActivo()) {
            throw new RuntimeException("No se puede actualizar un producto inactivo");
        }
        
        // Validar que el productoId no esté duplicado (si cambió)
        if (!producto.getProductoId().equals(productoActualizado.getProductoId())) {
            if (productoRepository.existsByProductoId(productoActualizado.getProductoId())) {
                throw new IllegalArgumentException("Ya existe un producto con el ID: " + productoActualizado.getProductoId());
            }
        }
        
        producto.setName(productoActualizado.getName());
        producto.setUnit(productoActualizado.getUnit());
        producto.setPrice(productoActualizado.getPrice());
        producto.setImg(productoActualizado.getImg());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setCategory(productoActualizado.getCategory());
        
        return productoRepository.save(producto);
    }
    
    public void delete(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        // Soft delete
        producto.setActivo(false);
        productoRepository.save(producto);
    }
    
    public boolean existsById(Long id) {
        return productoRepository.findById(id)
                .map(Producto::getActivo)
                .orElse(false);
    }
}

