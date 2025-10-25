package com.example.FARMACIA.service.implementaciones;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FARMACIA.Model.Detalle_ventas;
import com.example.FARMACIA.Model.Productos;
import com.example.FARMACIA.Model.Ventas;
import com.example.FARMACIA.Repository.Detalle_ventasRepository;
import com.example.FARMACIA.service.Detalle_ventaService;

@Service
public class Detalle_ventaServiceImplements implements Detalle_ventaService {

    @Autowired
    private Detalle_ventasRepository detalle_ventasRepository;

    @Override
    public Detalle_ventas AgregarDetalleVenta(Long idVenta, Long idProducto, int cantidad, double precioUnitario) {
        Detalle_ventas detalle = new Detalle_ventas();
        Ventas venta = new Ventas(); // Crear o buscar la entidad Ventas según sea necesario
        venta.setId(idVenta);
        Productos producto = new Productos(); // Crear o buscar la entidad Productos según sea necesario
        producto.setId(idProducto);
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecio_unitario(BigDecimal.valueOf(precioUnitario));
        return detalle_ventasRepository.save(detalle);
    }

    @Override
    public List<Detalle_ventas> ListarDetallesPorVenta(Long idVenta) {
        return detalle_ventasRepository.findByVentaId(idVenta);
    }

    @Override
    public void EliminarDetalleVenta(Long idDetalle) {
        Detalle_ventas detalle = detalle_ventasRepository.findById(idDetalle).orElse(null);
        if (detalle != null) {
            detalle_ventasRepository.delete(detalle);
        }
    }

    @Override
    public Detalle_ventas ActualizarDetalleVenta(Long idDetalle, int cantidad, double precioUnitario) {
        Detalle_ventas detalle = detalle_ventasRepository.findById(idDetalle).orElse(null);
        if (detalle != null) {
            detalle.setCantidad(cantidad);
            detalle.setPrecio_unitario(BigDecimal.valueOf(precioUnitario));
            return detalle_ventasRepository.save(detalle);
        }
        return null;
    }

    @Override
    public Detalle_ventas BuscarDetalleVenta(Long idDetalle) {
        return detalle_ventasRepository.findById(idDetalle).orElse(null);
    }
}


