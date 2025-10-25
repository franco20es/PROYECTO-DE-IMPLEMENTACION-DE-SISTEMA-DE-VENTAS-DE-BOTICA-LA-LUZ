package com.example.FARMACIA.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Detalle_ventas;
import com.example.FARMACIA.Repository.Detalle_ventasRepository;

@RestController
@RequestMapping("/detalle-ventas")
public class DetalleVentasController {
    /* Detalle_ventas AgregarDetalleVenta(Long idVenta, Long idProducto, int cantidad, double precioUnitario);
    void EliminarDetalleVenta(Long idDetalle);
    Detalle_ventas ActualizarDetalleVenta(Long idDetalle, int cantidad, double precioUnitario);
    Detalle_ventas BuscarDetalleVenta(Long idDetalle);
    List<Detalle_ventas> ListarDetallesPorVenta(Long idVenta); */
    @Autowired
    private Detalle_ventasRepository detalleVentasRepository;

    @GetMapping
    public Page<Detalle_ventas> listarDetalleVentas(
        @RequestParam(defaultValue = "") String cliente,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return detalleVentasRepository.findByVenta_Cliente_NombreContainingIgnoreCase(
            cliente, pageable
        );
    }
    @PostMapping
    public Detalle_ventas registrarDetalleVenta(@RequestBody Detalle_ventas nuevoDetalle) {
        return detalleVentasRepository.save(nuevoDetalle);
    }
    @PutMapping("/{id}")
    public Detalle_ventas actualizarDetalleVenta(@RequestBody Detalle_ventas detalleActualizado) {
        return detalleVentasRepository.save(detalleActualizado);
    }
    @DeleteMapping("/{id}")
    public void eliminarDetalleVenta(@PathVariable Long id) {
        detalleVentasRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public Detalle_ventas buscarDetalleVenta(@PathVariable Long id) {
        return detalleVentasRepository.findById(id).orElse(null);
    }
}
