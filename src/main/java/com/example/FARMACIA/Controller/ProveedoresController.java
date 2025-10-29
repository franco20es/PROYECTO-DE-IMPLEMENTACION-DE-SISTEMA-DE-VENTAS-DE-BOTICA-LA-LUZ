package com.example.FARMACIA.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Proveedores;
import com.example.FARMACIA.Repository.ProveedoresRepository;

@RestController
@RequestMapping("/proveedores")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ProveedoresController {
     @Autowired
    private ProveedoresRepository proveedoresRepository;

    // Listar todos los proveedores con filtro opcional por nombre
    @GetMapping
    public List<Proveedores> listarProveedores(@RequestParam(required = false) String nombre) {
        List<Proveedores> proveedores = proveedoresRepository.findAll();
        
        // Filtrar por nombre si se proporciona
        if (nombre != null && !nombre.isEmpty()) {
            proveedores = proveedores.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return proveedores;
    }

    // Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<?> crearProveedor(@RequestBody Proveedores proveedor) {
        if (proveedor.getNombre() == null || proveedor.getDireccion() == null || proveedor.getTelefono() == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios.");
        }
        Proveedores nuevoProveedor = proveedoresRepository.save(proveedor); // Guardar el proveedor en la base de datos
        return ResponseEntity.ok(nuevoProveedor);
    }

    // Actualizar un proveedor existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedores proveedor) {
        Optional<Proveedores> proveedorExistente = proveedoresRepository.findById(id);
        
        if (proveedorExistente.isPresent()) {
            Proveedores p = proveedorExistente.get();
            p.setNombre(proveedor.getNombre());
            p.setDireccion(proveedor.getDireccion());
            p.setTelefono(proveedor.getTelefono());
            p.setEmail(proveedor.getEmail());
            Proveedores proveedorActualizado = proveedoresRepository.save(p);
            return ResponseEntity.ok(proveedorActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un proveedor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        if (!proveedoresRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proveedoresRepository.deleteById(id); // Eliminar el proveedor de la base de datos
        return ResponseEntity.ok("Proveedor eliminado");
    }

    // Obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProveedorPorId(@PathVariable Long id) {
        Proveedores proveedor = proveedoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));
        return ResponseEntity.ok(proveedor);
    }
}
