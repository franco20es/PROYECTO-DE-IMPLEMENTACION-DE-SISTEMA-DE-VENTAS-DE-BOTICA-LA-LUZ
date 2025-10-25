package com.example.FARMACIA.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Proveedores;
import com.example.FARMACIA.Repository.ProveedoresRepository;

@RestController
@RequestMapping("/proveedores")
public class ProveedoresController {
     @Autowired
    private ProveedoresRepository proveedoresRepository;

    // Listar todos los proveedores
    @GetMapping
    public List<Proveedores> listarProveedores() {
        return proveedoresRepository.findAll(); // Recuperar todos los proveedores de la base de datos
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
        Proveedores proveedorExistente = proveedoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));
        proveedorExistente.setNombre(proveedor.getNombre());
        proveedorExistente.setDireccion(proveedor.getDireccion());
        proveedorExistente.setTelefono(proveedor.getTelefono());
        proveedorExistente.setEmail(proveedor.getEmail());
        Proveedores proveedorActualizado = proveedoresRepository.save(proveedorExistente); // Guardar los cambios en la base de datos
        return ResponseEntity.ok(proveedorActualizado);
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
