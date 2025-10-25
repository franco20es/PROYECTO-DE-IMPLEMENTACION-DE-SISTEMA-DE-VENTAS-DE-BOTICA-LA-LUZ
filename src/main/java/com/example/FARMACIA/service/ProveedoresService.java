package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Proveedores;


public interface  ProveedoresService {
    void RegistrarProveedor(String nombre, String direccion, String telefono);
    void ActualizarProveedor(Long id, String nombre, String direccion, String telefono);
    void EliminarProveedor(Long id);
    List<Proveedores> ListarProveedores();
}
