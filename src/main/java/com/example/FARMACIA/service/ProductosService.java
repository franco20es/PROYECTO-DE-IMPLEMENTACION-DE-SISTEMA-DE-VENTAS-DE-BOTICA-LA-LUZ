package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Productos;


public interface  ProductosService {
    void RegistrarProducto(String nombre, String descripcion, double precio, int stock);
    void ActualizarProducto(Long id, String nombre, String descripcion, double precio, int stock);
    void EliminarProducto(Long id);
    List<Productos> ListarProductos();
}
