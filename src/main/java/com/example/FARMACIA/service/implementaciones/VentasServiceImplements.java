package com.example.FARMACIA.service.implementaciones;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Clientes;
import com.example.FARMACIA.Model.Metodos_pago;
import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Model.Ventas;
import com.example.FARMACIA.Repository.VentasRepository;
import com.example.FARMACIA.service.VentasService;

@Service
public class VentasServiceImplements implements VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Override
    public void RegistarVentas(Long idCliente, Long idUsuario, Long idMetodoPago, LocalDateTime fecha, BigDecimal total, Long idProducto, int cantidad) {
        Ventas venta = new Ventas();
        Clientes cliente = new Clientes();
        cliente.setId(idCliente);
        Usuarios usuario = new Usuarios();
        usuario.setId(idUsuario);
        Metodos_pago metodoPago = new Metodos_pago();
        metodoPago.setId(idMetodoPago);
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setMetodoPago(metodoPago);
        venta.setFecha(fecha);
        venta.setTotal(total);
        // Lógica adicional para manejar el producto y la cantidad
        ventasRepository.save(venta);
    }

    @Override
    public void CancelarVentaVenta(Long idVenta) {
        ventasRepository.findById(idVenta).ifPresent(ventasRepository::delete);
    }

    @Override
    public void modificarVenta(Long idVenta, Long idCliente, Long idUsuario, Long idMetodoPago, LocalDateTime fecha, BigDecimal total, int nuevaCantidad) {
        Ventas venta = ventasRepository.findById(idVenta).orElse(null);
        if (venta != null) {
            Clientes cliente = new Clientes();
            cliente.setId(idCliente);
            Usuarios usuario = new Usuarios();
            usuario.setId(idUsuario);
            Metodos_pago metodoPago = new Metodos_pago();
            metodoPago.setId(idMetodoPago);
            venta.setCliente(cliente);
            venta.setUsuario(usuario);
            venta.setMetodoPago(metodoPago);
            venta.setFecha(fecha);
            venta.setTotal(total);
            // Lógica adicional para manejar la nueva cantidad
            ventasRepository.save(venta);
        }
    }

    @Override
    public List<Ventas> ListarVentas() {
        return ventasRepository.findAll();
    }
}