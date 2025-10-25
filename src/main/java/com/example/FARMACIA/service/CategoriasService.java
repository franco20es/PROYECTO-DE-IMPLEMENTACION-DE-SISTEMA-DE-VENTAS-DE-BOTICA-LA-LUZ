package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Categorias;

public interface CategoriasService {
    Categorias agregarCategoria(String nombre);
    Categorias actualizarCategoria(Long id, String nombre);
    void eliminarCategoria(Long id);
    Categorias buscarCategoria(Long id);
    List<Categorias> listarCategorias();
}