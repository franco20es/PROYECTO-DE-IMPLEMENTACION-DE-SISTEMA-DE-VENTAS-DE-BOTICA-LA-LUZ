package com.example.FARMACIA.service;

import com.example.FARMACIA.Model.Historial_stock;
import com.example.FARMACIA.Model.Productos;
import com.example.FARMACIA.Repository.Historial_stockRepository;
import com.example.FARMACIA.Repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class Historial_stockServiceImpl implements Historial_stockService {

    @Autowired
    private Historial_stockRepository historialStockRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public Historial_stock RegistrarStock(Long idProducto, int cantidad, String tipo) {
        Optional<Productos> productoOpt = productosRepository.findById(idProducto);
        if (!productoOpt.isPresent()) {
            throw new RuntimeException("Producto no encontrado");
        }
        Productos producto = productoOpt.get();
        int nuevoStock = producto.getStock();
        if ("Ingreso".equalsIgnoreCase(tipo)) {
            nuevoStock += cantidad;
        } else if ("Salida".equalsIgnoreCase(tipo)) {
            if (cantidad > nuevoStock) {
                throw new RuntimeException("Stock insuficiente para salida");
            }
            nuevoStock -= cantidad;
        } else {
            throw new RuntimeException("Tipo de movimiento inválido");
        }
        producto.setStock(nuevoStock);
        productosRepository.save(producto);

        Historial_stock historial = new Historial_stock();
        historial.setProducto(producto);
        historial.setCantidad(cantidad);
        historial.setTipo_movimiento(tipo);
        historial.setFecha(LocalDateTime.now());
        return historialStockRepository.save(historial);
    }

    @Override
    public Historial_stock EliminarRegistro(Long id) {
        Optional<Historial_stock> historialOpt = historialStockRepository.findById(id);
        if (!historialOpt.isPresent()) {
            throw new RuntimeException("Registro no encontrado");
        }
        historialStockRepository.deleteById(id);
        return historialOpt.get();
    }

    @Override
    public Historial_stock BuscarRegistro(Long id) {
        return historialStockRepository.findById(id).orElse(null);
    }

    @Override
    public Historial_stock ListarHistorialPorProducto(Long idProducto, String tipo) {
        // Implementa según tus necesidades, por ejemplo, usando un método custom en el repository
        return null;
    }
}
