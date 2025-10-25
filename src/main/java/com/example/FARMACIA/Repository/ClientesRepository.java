package com.example.FARMACIA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {
   Clientes findByNombre(String nombre);
}
