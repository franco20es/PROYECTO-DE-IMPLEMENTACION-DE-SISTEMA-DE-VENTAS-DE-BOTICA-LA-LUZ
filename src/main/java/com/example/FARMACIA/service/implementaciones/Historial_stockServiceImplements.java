package com.example.FARMACIA.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Historial_stock;
import com.example.FARMACIA.Model.Productos;
import com.example.FARMACIA.Repository.Historial_stockRepository;
import com.example.FARMACIA.service.Historial_stockService;

@Service
public class Historial_stockServiceImplements implements Historial_stockService {

    @Autowired
    private Historial_stockRepository historial_stockRepository;

    @Override
    public Historial_stock RegistrarStock(Long idProducto, int cantidad, String tipo) {
        Historial_stock historial = new Historial_stock();
        Productos producto = new Productos();
        producto.setId(idProducto);
        historial.setProducto(producto);
        historial.setCantidad(cantidad);
        historial.setTipo_movimiento(tipo);
        return historial_stockRepository.save(historial);
    }

    @Override
    public Historial_stock EliminarRegistro(Long id) {
        Historial_stock historial = historial_stockRepository.findById(id).orElse(null);
        if (historial != null) {
            historial_stockRepository.delete(historial);
        }
        return historial;
    }

    @Override
    public Historial_stock BuscarRegistro(Long id) {
        return historial_stockRepository.findById(id).orElse(null);
    }

    @Override
    public Historial_stock ListarHistorialPorProducto(Long idProducto, String tipo) {
        return historial_stockRepository.findByProductoId(idProducto).stream()
                .filter(h -> h.getTipo_movimiento().equals(tipo))
                .findFirst()
                .orElse(null);
    }
}
