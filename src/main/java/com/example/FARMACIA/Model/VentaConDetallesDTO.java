package com.example.FARMACIA.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para recibir una venta completa con todos sus detalles
 * Esto permite crear la venta y todos los detalles en una sola petición
 */
public class VentaConDetallesDTO {
    
    private Clientes cliente;
    private Usuarios usuario;
    private Metodos_pago metodos_pago;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<DetalleVentaDTO> detalles; // Lista de productos en el carrito

    // Constructor vacío
    public VentaConDetallesDTO() {
    }

    // Constructor con parámetros
    public VentaConDetallesDTO(Clientes cliente, Usuarios usuario, Metodos_pago metodos_pago, 
                                LocalDateTime fecha, BigDecimal total, List<DetalleVentaDTO> detalles) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.metodos_pago = metodos_pago;
        this.fecha = fecha;
        this.total = total;
        this.detalles = detalles;
    }

    // Getters y Setters
    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Metodos_pago getMetodos_pago() {
        return metodos_pago;
    }

    public void setMetodos_pago(Metodos_pago metodos_pago) {
        this.metodos_pago = metodos_pago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }
}
