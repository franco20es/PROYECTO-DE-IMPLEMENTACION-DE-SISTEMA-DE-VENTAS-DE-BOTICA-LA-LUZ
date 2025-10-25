package com.example.FARMACIA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Productos;

public interface  ProductosRepository extends JpaRepository<Productos, Long> {
    
}
