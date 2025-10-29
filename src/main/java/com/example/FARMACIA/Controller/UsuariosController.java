package com.example.FARMACIA.Controller;

import java.nio.file.OpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")

public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar todos los usuarios
    @GetMapping
    public List<Usuarios> obtenerUsuarios(@RequestParam(required = false) String nombre) {

        List<Usuarios> usuarios = usuarioRepository.findAll();

        // Filtrar por nombre si se proporciona

        if (nombre != null && !nombre.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(u -> u.getNombre().contains(nombre))
                    .toList();
        }
        return usuarios;

    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuarios usuario) {
        // Validar email único antes de crear
        String email = usuario.getEmail();
        if (email != null)
            email = email.trim();
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio.");
        }
        Usuarios existente = usuarioRepository.findByEmail(email);
        if (existente != null) {
            return ResponseEntity.badRequest().body("El email ya está en uso por otro usuario.");
        }
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        usuario.setFecha_registro(LocalDate.now());
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEmail(email);
        Usuarios nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PutMapping("/{id}")
public Usuarios actualizarUsuario(@PathVariable Long id, @RequestBody Usuarios usuarioActualizado) {
    Optional<Usuarios> usuarioExistente = usuarioRepository.findById(id);
    if (usuarioExistente.isPresent()) {
        Usuarios usuario = usuarioExistente.get();
        // Solo validar email duplicado si el email realmente cambia
        if (!usuario.getEmail().equals(usuarioActualizado.getEmail())) {
            Usuarios usuarioConMismoEmail = usuarioRepository.findByEmail(usuarioActualizado.getEmail());
            if (usuarioConMismoEmail != null) {
                throw new RuntimeException("El email ya está en uso por otro usuario.");
            }
            usuario.setEmail(usuarioActualizado.getEmail());
        }
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        return usuarioRepository.save(usuario);
    } else {
        throw new RuntimeException("Usuario no encontrado con id: " + id);
    }
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