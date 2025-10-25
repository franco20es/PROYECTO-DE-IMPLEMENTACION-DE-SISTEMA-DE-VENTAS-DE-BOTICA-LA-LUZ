package com.example.FARMACIA.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Clientes;
import com.example.FARMACIA.Repository.ClientesRepository;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    /*  Clientes buscarPorNombre(String nombre);
    Clientes registrarClientes(Clientes cliente);
    Clientes buscarPorId(Long id);
    List<Clientes> listarTodos();
    Clientes agregarCliente(Clientes cliente);
    Clientes actualizarCliente(Long id, Clientes cliente);
    void eliminarCliente(Long id);*/
    @Autowired
    private ClientesRepository clientesRepository;
    @GetMapping
    public List<Clientes> listarClientes() {
        return clientesRepository.findAll();
    }
    @PostMapping
    public Clientes registrarCliente(@RequestBody Clientes cliente) {
        return clientesRepository.save(cliente);
    }
    @PutMapping("/{id}")
    public Clientes actualizarCliente(@PathVariable Long id, @RequestBody Clientes clienteActualizado) {
        clienteActualizado.setId(id);
        return clientesRepository.save(clienteActualizado);
    }
    @GetMapping("/{id}")
    public Clientes buscarClientePorId(@PathVariable Long id) {
        return clientesRepository.findById(id).orElse(null);
    }
    @GetMapping("/nombre/{nombre}")
    public Clientes buscarClientePorNombre(@PathVariable String nombre) {
        return clientesRepository.findByNombre(nombre);
    }
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clientesRepository.deleteById(id);
    }
}
