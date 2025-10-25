package com.example.FARMACIA.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Repository.UsuarioRepository;
import com.example.FARMACIA.service.UsuariosService;

@Service
public class UsuariosServiceImplements implements UsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void RegistrarUsuario(String nombre, String email, String password) {
        Usuarios usuario = new Usuarios();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuarioRepository.save(usuario);
    }

    @Override
    public void ActualizarUsuario(Long id, String nombre, String email, String password) {
        Usuarios usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuarioRepository.save(usuario);
        }
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
