package com.example.FARMACIA.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Productos;
import com.example.FARMACIA.Repository.ProductosRepository;
import com.example.FARMACIA.service.ProductosService;

@Service
public class ProductosServiceImplements implements ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public void RegistrarProducto(String nombre, String descripcion, double precio, int stock) {
        Productos producto = new Productos();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        productosRepository.save(producto);
    }

    @Override
    public void ActualizarProducto(Long id, String nombre, String descripcion, double precio, int stock) {
        Productos producto = productosRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setStock(stock);
            productosRepository.save(producto);
        }
    }

    @Override
    public void EliminarProducto(Long id) {
        productosRepository.findById(id).ifPresent(productosRepository::delete);
    }

    @Override
    public List<Productos> ListarProductos() {
        return productosRepository.findAll();
    }
}