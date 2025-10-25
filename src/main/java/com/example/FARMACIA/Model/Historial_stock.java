package com.example.FARMACIA.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_stock")
public class Historial_stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Productos producto;

	private Integer cantidad;
	private LocalDateTime fecha;
	private String tipo_movimiento;

	public Historial_stock() {}

	public Historial_stock(Productos producto, Integer cantidad, LocalDateTime fecha, String tipo_movimiento) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.tipo_movimiento = tipo_movimiento;
	}

	// Getters y setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Productos getProducto() { return producto; }
	public void setProducto(Productos producto) { this.producto = producto; }

	public Integer getCantidad() { return cantidad; }
	public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

	public LocalDateTime getFecha() { return fecha; }
	public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

	public String getTipo_movimiento() { return tipo_movimiento; }
	public void setTipo_movimiento(String tipo_movimiento) { this.tipo_movimiento = tipo_movimiento; }
}
