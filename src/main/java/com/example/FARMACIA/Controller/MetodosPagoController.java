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

import com.example.FARMACIA.Model.Metodos_pago;
import com.example.FARMACIA.Repository.MetodosPagoRepository;

@RestController
@RequestMapping("/metodos-pago")
public class MetodosPagoController {
/* void AgregarMetodoPago(String tipo, String detalles);
void EliminarMetodoPago(Long id);
Metodos_pago BuscarMetodoPago(Long id);
List<Metodos_pago> ListarMetodosPago(); */
    @Autowired
    private MetodosPagoRepository metodosPagoRepository;

    @GetMapping
    public List<Metodos_pago> listarMetodosPago() {
        return metodosPagoRepository.findAll(); // Recuperar todos los métodos de pago de la base de datos
    }

    @PostMapping
    public Metodos_pago agregarMetodoPago(@RequestBody Metodos_pago nuevoMetodo) {
        // Guardar el método de pago en la base de datos
        return metodosPagoRepository.save(nuevoMetodo);
    }
    @PutMapping("/{id}")
    public Metodos_pago actualizarMetodoPago(@PathVariable Long id, @RequestBody Metodos_pago metodoActualizado) {
        metodoActualizado.setId(id);
        return metodosPagoRepository.save(metodoActualizado);
    }
    @DeleteMapping("/{id}")
    public void eliminarMetodoPago(@PathVariable Long id) {
        metodosPagoRepository.deleteById(id); // Eliminar el método de pago de la base de datos
    }

}
