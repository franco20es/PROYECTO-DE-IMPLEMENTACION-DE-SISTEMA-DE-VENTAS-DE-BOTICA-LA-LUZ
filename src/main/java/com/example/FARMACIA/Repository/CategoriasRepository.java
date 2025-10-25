package com.example.FARMACIA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Categorias;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    Categorias findByNombre(String nombre);
}