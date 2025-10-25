package com.example.FARMACIA.Model;

import java.math.BigDecimal;

/**
 * DTO para representar un detalle de venta individual
 * Contiene la información de un producto dentro del carrito
 */
public class DetalleVentaDTO {
    
    private Productos producto;
    private Integer cantidad;
    private BigDecimal precio_unitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    // Constructor vacío
    public DetalleVentaDTO() {
    }

    // Constructor con parámetros
    public DetalleVentaDTO(Productos producto, Integer cantidad, BigDecimal precio_unitario, 
                           BigDecimal descuento, BigDecimal subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.descuento = descuento;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
