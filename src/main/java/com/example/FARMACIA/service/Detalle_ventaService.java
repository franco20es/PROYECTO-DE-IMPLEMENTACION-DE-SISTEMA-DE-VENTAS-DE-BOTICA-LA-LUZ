package com.example.FARMACIA.service;

import java.util.List;

import com.example.FARMACIA.Model.Detalle_ventas;


public interface  Detalle_ventaService {
 Detalle_ventas AgregarDetalleVenta(Long idVenta, Long idProducto, int cantidad, double precioUnitario);
    void EliminarDetalleVenta(Long idDetalle);
    Detalle_ventas ActualizarDetalleVenta(Long idDetalle, int cantidad, double precioUnitario);
    Detalle_ventas BuscarDetalleVenta(Long idDetalle);
    List<Detalle_ventas> ListarDetallesPorVenta(Long idVenta);
}
