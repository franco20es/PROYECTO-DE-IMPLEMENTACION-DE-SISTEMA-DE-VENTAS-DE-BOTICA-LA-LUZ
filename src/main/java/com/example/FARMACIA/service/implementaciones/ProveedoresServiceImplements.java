package com.example.FARMACIA.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Proveedores;
import com.example.FARMACIA.Repository.ProveedoresRepository;
import com.example.FARMACIA.service.ProveedoresService;

@Service
public class ProveedoresServiceImplements implements ProveedoresService {

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Override
    public void RegistrarProveedor(String nombre, String direccion, String telefono) {
        Proveedores proveedor = new Proveedores();
        proveedor.setNombre(nombre);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedoresRepository.save(proveedor);
    }

    @Override
    public void ActualizarProveedor(Long id, String nombre, String direccion, String telefono) {
        Proveedores proveedor = proveedoresRepository.findById(id).orElse(null);
        if (proveedor != null) {
            proveedor.setNombre(nombre);
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
            proveedoresRepository.save(proveedor);
        }
    }

    @Override
    public void EliminarProveedor(Long id) {
        proveedoresRepository.findById(id).ifPresent(proveedoresRepository::delete);
    }

    @Override
    public List<Proveedores> ListarProveedores() {
        return proveedoresRepository.findAll();
    }
}