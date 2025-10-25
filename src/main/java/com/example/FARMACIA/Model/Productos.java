package com.example.FARMACIA.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedores proveedor;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categorias categoria;

    public Productos() {
    }

    public Productos(String nombre, Double precio, String descripcion, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
