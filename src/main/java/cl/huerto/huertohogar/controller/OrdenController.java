package cl.huerto.huertohogar.controller;

import cl.huerto.huertohogar.model.Orden;
import cl.huerto.huertohogar.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
@Tag(name = "Órdenes", description = "Endpoints para gestión de órdenes de compra")
@CrossOrigin(origins = "*")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private cl.huerto.huertohogar.repository.UsuarioRepository usuarioRepository;

    // ===================== LISTAR TODAS LAS ÓRDENES (SOLO ADMIN) =====================
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Listar todas las órdenes",
            description = "Obtiene todas las órdenes (solo ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<Orden>> getAllOrdenes() {
        List<Orden> ordenes = ordenService.findAll();
        return ResponseEntity.ok(ordenes);
    }

    // ===================== MIS ÓRDENES (USER O ADMIN) =====================
    @GetMapping("/mis-ordenes")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @Operation(
            summary = "Obtener mis órdenes",
            description = "Obtiene las órdenes del usuario autenticado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<Orden>> getMisOrdenes(Authentication authentication) {
        String email = authentication.getName();
        Long usuarioId = obtenerUsuarioIdDesdeEmail(email);
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Orden> ordenes = ordenService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(ordenes);
    }

    // ===================== OBTENER POR ID =====================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @Operation(
            summary = "Obtener orden por ID",
            description = "Obtiene una orden específica por su ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Orden> getOrdenById(@PathVariable Long id) {
        Optional<Orden> orden = ordenService.findById(id);
        return orden.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ===================== CREAR ORDEN =====================
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @Operation(
            summary = "Crear orden",
            description = "Crea una nueva orden de compra",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Orden> createOrden(
            @RequestBody List<OrdenService.OrdenItemData> items,
            Authentication authentication) {

        try {
            Long usuarioId = obtenerUsuarioIdDesdeEmail(authentication.getName());
            if (usuarioId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Orden orden = ordenService.createOrden(usuarioId, items);
            return ResponseEntity.status(HttpStatus.CREATED).body(orden);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ===================== CONFIRMAR ORDEN =====================
    @PostMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @Operation(
            summary = "Confirmar orden",
            description = "Confirma una orden pendiente",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Orden> confirmarOrden(@PathVariable Long id) {
        try {
            Orden orden = ordenService.confirmarOrden(id);
            return ResponseEntity.ok(orden);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ===================== CANCELAR ORDEN =====================
    @PostMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @Operation(
            summary = "Cancelar orden",
            description = "Cancela una orden pendiente",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Orden> cancelarOrden(@PathVariable Long id) {
        try {
            Orden orden = ordenService.cancelarOrden(id);
            return ResponseEntity.ok(orden);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ===================== MÉTODO AUXILIAR =====================
    private Long obtenerUsuarioIdDesdeEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> usuario.getId())
                .orElse(null);
    }
}
