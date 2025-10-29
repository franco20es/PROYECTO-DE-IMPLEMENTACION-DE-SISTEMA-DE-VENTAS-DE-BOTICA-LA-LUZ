
package com.example.FARMACIA.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Repository.UsuarioRepository;
import com.example.FARMACIA.service.UsuariosService;

@Service

public class UsuariosServiceImplements implements UsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void RegistrarUsuario(String nombre, String email, String password) {
        Usuarios usuario = new Usuarios();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);
    }

    @Override
    public void ActualizarUsuario(Long id, String nombre, String email, String password) {
        Usuarios usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            System.out.println("usuario no encontrado" + id);
        }
        usuario.setNombre(nombre);
        // Solo validar email duplicado si el email realmente cambia
        if (email != null && !email.equals(usuario.getEmail())) {
            Usuarios usuarioConMismoEmail = usuarioRepository.findByEmail(email);
            if (usuarioConMismoEmail != null) {
               System.out.println("El email ya está en uso por otro usuario.");
            }
            usuario.setEmail(email);
        }
        // Solo encripta si la contraseña no está vacía
        if (password != null && !password.isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public void EliminarUsuario(Long id) {
        usuarioRepository.findById(id).ifPresent(usuarioRepository::delete);
    }

    @Override
    public List<Usuarios> ListarUsuarios() {
        return usuarioRepository.findAll();
    }
}
