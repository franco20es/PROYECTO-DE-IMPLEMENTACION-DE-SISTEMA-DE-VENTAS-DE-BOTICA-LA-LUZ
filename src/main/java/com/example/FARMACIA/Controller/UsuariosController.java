package com.example.FARMACIA.Controller;

import java.time.LocalDate;
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

import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos los usuarios
    @GetMapping
    public List<Usuarios> obtenerUsuarios() {
        return usuarioRepository.findAll(); // Recuperar todos los usuarios de la base de datos
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuarios usuario) {
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        usuario.setFecha_registro(LocalDate.now()); // Asignar la fecha de registro actual
        Usuarios nuevoUsuario = usuarioRepository.save(usuario); // Guardar el usuario en la base de datos
        return ResponseEntity.ok(nuevoUsuario);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        Usuarios usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setPassword(usuario.getPassword());
        usuarioExistente.setRol(usuario.getRol());
        Usuarios usuarioActualizado = usuarioRepository.save(usuarioExistente); // Guardar los cambios en la base de datos
        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id); // Eliminar el usuario de la base de datos
        return ResponseEntity.ok("Usuario eliminado");
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        return ResponseEntity.ok(usuario);
    }
}