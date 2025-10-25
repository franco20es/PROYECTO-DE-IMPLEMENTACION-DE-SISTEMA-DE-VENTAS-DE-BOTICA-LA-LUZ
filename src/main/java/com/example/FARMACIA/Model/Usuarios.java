package com.example.FARMACIA.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotNull(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    @NotNull(message = "El email es obligatorio.")
    @Email(message = "El formato del email no es válido.")
    private String email;

    @NotNull(message = "La contraseña es obligatoria.")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres.")
    private String password;

    private String rol;
    private LocalDate fecha_registro;

    // Constructor
    public Usuarios(String nombre, String email, String password, String rol, LocalDate fecha_registro) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.fecha_registro = fecha_registro;
    }

    public Usuarios() {
    }

    // Getters y Setters
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public LocalDate getFecha_registro() {
        return fecha_registro;
    }
    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}