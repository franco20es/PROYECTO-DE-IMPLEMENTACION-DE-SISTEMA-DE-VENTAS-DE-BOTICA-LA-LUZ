package com.example.FARMACIA.service;

import com.example.FARMACIA.Model.Historial_stock;


public interface  Historial_stockService {
Historial_stock RegistrarStock(Long idProducto, int cantidad, String tipo);
Historial_stock EliminarRegistro(Long id);
Historial_stock BuscarRegistro(Long id);
Historial_stock ListarHistorialPorProducto(Long idProducto, String tipo);

}
