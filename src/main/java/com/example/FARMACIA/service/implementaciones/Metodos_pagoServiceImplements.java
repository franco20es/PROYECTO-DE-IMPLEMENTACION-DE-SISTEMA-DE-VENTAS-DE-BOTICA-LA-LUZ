package com.example.FARMACIA.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Metodos_pago;
import com.example.FARMACIA.Repository.MetodosPagoRepository;
import com.example.FARMACIA.service.MetodosPagoService;
@Service
public class Metodos_pagoServiceImplements implements MetodosPagoService {

    @Autowired
    private MetodosPagoRepository metodosPagoRepository;

    @Override
    public void AgregarMetodoPago(String tipo, String detalles) {
        Metodos_pago metodo = new Metodos_pago();
        metodo.setNombre(tipo);
        metodosPagoRepository.save(metodo);
    }

    @Override
    public void EliminarMetodoPago(Long id) {
        Metodos_pago metodo = metodosPagoRepository.findById(id).orElse(null);
        if (metodo != null) {
            metodosPagoRepository.delete(metodo);
        }
    }

    @Override
    public Metodos_pago BuscarMetodoPago(Long id) {
        return metodosPagoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Metodos_pago> ListarMetodosPago() {
        return metodosPagoRepository.findAll();
    }
}
