package com.example.FARMACIA.service.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.FARMACIA.Model.Categorias;
import com.example.FARMACIA.Repository.CategoriasRepository;
import com.example.FARMACIA.service.CategoriasService;

import java.util.List;

@Service
public class CategoriaServiceImplements implements CategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Override
    public Categorias agregarCategoria(String nombre) {
        if (categoriasRepository.findByNombre(nombre) != null) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
        Categorias categoria = new Categorias();
        categoria.setNombre(nombre);
        return categoriasRepository.save(categoria);
    }

    @Override
    public Categorias actualizarCategoria(Long id, String nombre) {
        Categorias categoria = categoriasRepository.findById(id).orElse(null);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
        categoria.setNombre(nombre);
        return categoriasRepository.save(categoria);
    }

    @Override
    public void eliminarCategoria(Long id) {
        if (!categoriasRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
        categoriasRepository.deleteById(id);
    }

    @Override
    public Categorias buscarCategoria(Long id) {
        return categoriasRepository.findById(id).orElse(null);
    }

    @Override
    public List<Categorias> listarCategorias() {
        return categoriasRepository.findAll();
    }
}