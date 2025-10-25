package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Clientes;

public interface ClientesService {
    Clientes buscarPorNombre(String nombre);
    Clientes registrarClientes(Clientes cliente);
    Clientes buscarPorId(Long id);
    List<Clientes> listarTodos();
    Clientes agregarCliente(Clientes cliente);
    Clientes actualizarCliente(Long id, Clientes cliente);
    void eliminarCliente(Long id);
}