package com.example.FARMACIA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Usuarios findByEmail(String email);
}


