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

import com.example.FARMACIA.Model.Historial_stock;
import com.example.FARMACIA.Repository.Historial_stockRepository;

@RestController
@RequestMapping("/historial-stock")
public class HistorialStockController {
    /* Historial_stock RegistrarStock(Long idProducto, int cantidad, String tipo);
    Historial_stock EliminarRegistro(Long id);
    Historial_stock BuscarRegistro(Long id);
    Historial_stock ListarHistorialPorProducto(Long idProducto, String tipo);*/
    @Autowired
    private Historial_stockRepository historial_stockRepository;
   @GetMapping
   public List<Historial_stock> listarHistorialStock() {
       return historial_stockRepository.findAll();
   }
   @PostMapping
   public Historial_stock registrarStock(@RequestBody Historial_stock nuevoHistorial) {
       return historial_stockRepository.save(nuevoHistorial);
   }
   @PutMapping("/{id}")
   public Historial_stock actualizarStock(@PathVariable Long id, @RequestBody Historial_stock historialActualizado) {
       historialActualizado.setId(id);
       return historial_stockRepository.save(historialActualizado);
   }
   @DeleteMapping("/{id}")
   public void eliminarRegistro(@PathVariable Long id) {
       historial_stockRepository.deleteById(id);
   }
}
