package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Usuarios;


public interface  UsuariosService {
 void RegistrarUsuario(String nombre, String email, String password);
 void ActualizarUsuario(Long id, String nombre, String email, String password);
    void EliminarUsuario(Long id);
    List<Usuarios> ListarUsuarios();
}
