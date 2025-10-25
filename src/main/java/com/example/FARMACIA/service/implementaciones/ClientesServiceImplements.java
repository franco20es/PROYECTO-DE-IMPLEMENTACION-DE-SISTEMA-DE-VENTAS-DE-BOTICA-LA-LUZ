
package com.example.FARMACIA.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Clientes;
import com.example.FARMACIA.Repository.ClientesRepository;
import com.example.FARMACIA.service.ClientesService;

@Service
public class ClientesServiceImplements implements ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    @Override
    public Clientes buscarPorNombre(String nombre) {
        return clientesRepository.findByNombre(nombre);
    }

    @Override
    public Clientes buscarPorId(Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Clientes> listarTodos() {
        return clientesRepository.findAll();
    }

    @Override
    public Clientes agregarCliente(Clientes cliente) {
        return clientesRepository.save(cliente);
    }

    @Override
    public Clientes actualizarCliente(Long id, Clientes cliente) {
        Clientes clienteExistente = clientesRepository.findById(id).orElse(null);
        if (clienteExistente != null) {
            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setApellido(cliente.getApellido());
            clienteExistente.setTelefono(cliente.getTelefono());

            // Actualiza otros campos según sea necesario
            return clientesRepository.save(clienteExistente);
        }
        return null;
    }

    @Override
    public void eliminarCliente(Long id) {
        clientesRepository.deleteById(id);
    }
    @Override
    public Clientes registrarClientes(Clientes cliente) {
        return clientesRepository.save(cliente);
    }
}