package cl.huerto.huertohogar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "El ID del producto es obligatorio")
    private String productoId;
    
    @Column(nullable = false, length = 200)
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "La unidad es obligatoria")
    private String unit;
    
    @Column(nullable = false)
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Integer price;
    
    @Column(length = 500)
    private String img;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "La categoría es obligatoria")
    private String category;
    
    @Column(nullable = false)
    private Boolean activo = true;
}

