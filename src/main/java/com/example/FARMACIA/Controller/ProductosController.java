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

import com.example.FARMACIA.Model.Productos;
import com.example.FARMACIA.Repository.ProductosRepository;

@RestController
@RequestMapping("/productos")
public class ProductosController {
     /* void RegistrarProducto(String nombre, String descripcion, double precio, int stock);
     void ActualizarProducto(Long id, String nombre, String descripcion, double precio, int stock);
     void EliminarProducto(Long id);
     List<Productos> ListarProductos();*/
    @Autowired
    private ProductosRepository productosRepository; 
    @GetMapping
    public List<Productos> listarProductos() {
        return productosRepository.findAll(); // Recuperar todos los productos de la base de datos
     }
     @PostMapping
     public Productos agregarProducto(@RequestBody Productos nuevoProducto) {
        // Guardar el producto en la base de datos
        return productosRepository.save(nuevoProducto);
     }
     @PutMapping("/{id}")
     public Productos actualizarProducto(@PathVariable Long id, @RequestBody Productos productoActualizado) {
        // Actualizar el producto en la base de datos
        productoActualizado.setId(id);
        return productosRepository.save(productoActualizado);
     }
     @DeleteMapping("/{id}")
     public void eliminarProducto(@PathVariable Long id) {
        productosRepository.deleteById(id); // Eliminar el producto de la base de datos
     }
     
}
