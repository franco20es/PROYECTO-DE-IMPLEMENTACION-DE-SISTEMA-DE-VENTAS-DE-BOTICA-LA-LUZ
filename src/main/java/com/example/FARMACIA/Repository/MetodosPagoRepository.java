package com.example.FARMACIA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Metodos_pago;

public interface  MetodosPagoRepository extends JpaRepository<Metodos_pago, Long> {
    

}
