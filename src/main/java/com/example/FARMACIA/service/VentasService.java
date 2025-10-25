package com.example.FARMACIA.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.FARMACIA.Model.Ventas;


public interface  VentasService {
   void RegistarVentas(Long idCliente, Long idUsuario, Long idMetodoPago, LocalDateTime fecha, BigDecimal total, Long idProducto, int cantidad);
   void CancelarVentaVenta(Long idVenta);
   void modificarVenta(Long idVenta, Long idCliente, Long idUsuario, Long idMetodoPago, LocalDateTime fecha, BigDecimal total, int nuevaCantidad);
   List<Ventas> ListarVentas();
}
