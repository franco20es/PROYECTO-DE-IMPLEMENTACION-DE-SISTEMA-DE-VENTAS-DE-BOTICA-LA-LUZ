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

import com.example.FARMACIA.Model.Productos;
import com.example.FARMACIA.Repository.ProductosRepository;

@RestController
@RequestMapping("/productos")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ProductosController {
    @Autowired
    private ProductosRepository productosRepository; 
    
    @GetMapping
    public List<Productos> listarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        
        List<Productos> productos = productosRepository.findAll();
        
        // Filtrar por nombre si se proporciona
        if (nombre != null && !nombre.isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        // Filtrar por precio mínimo
        if (precioMin != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() >= precioMin)
                    .collect(Collectors.toList());
        }
        
        // Filtrar por precio máximo
        if (precioMax != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() <= precioMax)
                    .collect(Collectors.toList());
        }
        
        return productos;
     }
     @PostMapping
     public Productos agregarProducto(@RequestBody Productos nuevoProducto) {
        // Guardar el producto en la base de datos
        return productosRepository.save(nuevoProducto);
     }
     @PutMapping("/{id}")
     public Productos actualizarProducto(@PathVariable Long id, @RequestBody Productos productoActualizado) {
        // Validar campos obligatorios
        if (productoActualizado.getNombre() == null || productoActualizado.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
        }
        if (productoActualizado.getDescripcion() == null || productoActualizado.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción del producto no puede ser nula o vacía");
        }
        if (productoActualizado.getPrecio() == null || productoActualizado.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero");
        }

        // Buscar el producto existente
        Optional<Productos> productoExistente = productosRepository.findById(id);

        if (productoExistente.isPresent()) {
            Productos producto = productoExistente.get();
            // Actualizar solo los campos necesarios
            producto.setNombre(productoActualizado.getNombre());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            return productosRepository.save(producto);
        } else {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
     }
     @DeleteMapping("/{id}")
     public void eliminarProducto(@PathVariable Long id) {
        productosRepository.deleteById(id); // Eliminar el producto de la base de datos
     }
     
}
