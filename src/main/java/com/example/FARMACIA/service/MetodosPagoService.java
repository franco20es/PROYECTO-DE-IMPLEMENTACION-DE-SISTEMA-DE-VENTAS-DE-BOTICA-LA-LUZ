package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Metodos_pago;


public interface  MetodosPagoService {
   void AgregarMetodoPago(String tipo, String detalles);
    void EliminarMetodoPago(Long id);
    Metodos_pago BuscarMetodoPago(Long id);
    List<Metodos_pago> ListarMetodosPago();
}
