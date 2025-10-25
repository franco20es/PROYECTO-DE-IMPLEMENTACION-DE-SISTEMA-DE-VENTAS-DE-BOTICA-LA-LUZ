package com.example.FARMACIA.Model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_ventas")
public class Detalle_ventas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_venta")
	private Ventas venta;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Productos producto;

	private Integer cantidad;
	private BigDecimal precio_unitario;
	private BigDecimal subtotal;
	private BigDecimal descuento;

	public Detalle_ventas() {}

	public Detalle_ventas(Ventas venta, Productos producto, Integer cantidad, BigDecimal precio_unitario, BigDecimal subtotal, BigDecimal descuento) {
		this.venta = venta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio_unitario = precio_unitario;
		this.subtotal = subtotal;
		this.descuento = descuento;
	}

	// Getters y setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Ventas getVenta() { return venta; }
	public void setVenta(Ventas venta) { this.venta = venta; }

	public Productos getProducto() { return producto; }
	public void setProducto(Productos producto) { this.producto = producto; }

	public Integer getCantidad() { return cantidad; }
	public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

	public BigDecimal getPrecio_unitario() { return precio_unitario; }
	public void setPrecio_unitario(BigDecimal precio_unitario) { this.precio_unitario = precio_unitario; }

	public BigDecimal getSubtotal() { return subtotal; }
	public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

	public BigDecimal getDescuento() { return descuento; }
	public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
}
