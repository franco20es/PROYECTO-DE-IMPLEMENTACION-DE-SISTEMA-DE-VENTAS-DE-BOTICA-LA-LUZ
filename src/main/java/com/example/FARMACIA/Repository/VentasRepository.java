package com.example.FARMACIA.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Ventas;

public interface  VentasRepository extends JpaRepository<Ventas, Long> {
    long countByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
