package com.example.FARMACIA.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Categorias;
import com.example.FARMACIA.Repository.CategoriasRepository;
import com.example.FARMACIA.service.CategoriasService;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private CategoriasService categoriasService; // Inyectar el servicio correctamente

    // Listar todas las categorías
    @GetMapping
    public List<Categorias> listarCategorias() {
        return categoriasService.listarCategorias(); // Usar el servicio para listar categorías
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody Categorias categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la categoría es obligatorio.");
        }
        Categorias nuevaCategoria = categoriasService.agregarCategoria(categoria.getNombre());
        return ResponseEntity.ok(nuevaCategoria);
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categorias categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la categoría es obligatorio.");
        }
        Categorias categoriaActualizada = categoriasService.actualizarCategoria(id, categoria.getNombre());
        return ResponseEntity.ok(categoriaActualizada);
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        if (!categoriasRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriasService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoría eliminada");
    }

    // Buscar una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoria(@PathVariable Long id) {
        Categorias categoria = categoriasService.buscarCategoria(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }
}