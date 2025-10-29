package com.example.FARMACIA.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Clientes;
import com.example.FARMACIA.Repository.ClientesRepository;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ClientesController {
   
    @Autowired
    private ClientesRepository clientesRepository;
    
    @GetMapping
    public List<Clientes> listarClientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido) {
        
        List<Clientes> clientes = clientesRepository.findAll();
        
        // Filtrar por nombre si se proporciona
        if (nombre != null && !nombre.isEmpty()) {
            clientes = clientes.stream()
                    .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        // Filtrar por apellido si se proporciona
        if (apellido != null && !apellido.isEmpty()) {
            clientes = clientes.stream()
                    .filter(c -> c.getApellido().toLowerCase().contains(apellido.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return clientes;
    }
    @PostMapping
    public Clientes registrarCliente(@RequestBody Clientes cliente) {
        return clientesRepository.save(cliente);
    }
    @PutMapping("/{id}")
    public Clientes actualizarCliente(@PathVariable Long id, @RequestBody Clientes clienteActualizado) {
        // Buscar el cliente existente
        Optional<Clientes> clienteExistente = clientesRepository.findById(id);
        
        if (clienteExistente.isPresent()) {
            Clientes cliente = clienteExistente.get();
            // Actualizar solo los campos necesarios
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setTelefono(clienteActualizado.getTelefono());
            return clientesRepository.save(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
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
