package cl.huerto.huertohogar.repository;

import cl.huerto.huertohogar.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByProductoId(String productoId);
    
    List<Producto> findByActivoTrue();
    
    List<Producto> findByCategoryAndActivoTrue(String category);
    
    boolean existsByProductoId(String productoId);
}

