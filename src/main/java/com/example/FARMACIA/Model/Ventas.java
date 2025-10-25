
package com.example.FARMACIA.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
public class Ventas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Clientes cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Usuarios usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_metodo")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Metodos_pago metodoPago;

	private LocalDateTime fecha;

	private BigDecimal total;

	public Ventas() {}

	public Ventas(Clientes cliente, Usuarios usuario, Metodos_pago metodoPago, LocalDateTime fecha, BigDecimal total) {
		this.cliente = cliente;
		this.usuario = usuario;
		this.metodoPago = metodoPago;
		this.fecha = fecha;
		this.total = total;
	}

	// Getters y setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Clientes getCliente() { return cliente; }
	public void setCliente(Clientes cliente) { this.cliente = cliente; }

	public Usuarios getUsuario() { return usuario; }
	public void setUsuario(Usuarios usuario) { this.usuario = usuario; }

	public Metodos_pago getMetodoPago() { return metodoPago; }
	public void setMetodoPago(Metodos_pago metodoPago) { this.metodoPago = metodoPago; }

	public LocalDateTime getFecha() { return fecha; }
	public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

	public BigDecimal getTotal() { return total; }
	public void setTotal(BigDecimal total) { this.total = total; }
    
}
