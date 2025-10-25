package com.example.FARMACIA.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Repository.ClientesRepository;
import com.example.FARMACIA.Repository.ProductosRepository;
import com.example.FARMACIA.Repository.ProveedoresRepository;
import com.example.FARMACIA.Repository.VentasRepository;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private VentasRepository ventasRepository;
    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @GetMapping("/totales")
    public Map<String, Object> getTotales() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalProductos", productosRepository.count());
        data.put("totalClientes", clientesRepository.count());
        data.put("totalProveedores", proveedoresRepository.count());
        data.put("totalVentas", ventasRepository.count());
        // Ventas del día
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(LocalTime.MAX);
        data.put("ventasHoy", ventasRepository.countByFechaBetween(inicioDia, finDia));
        // Ventas de la semana
        LocalDateTime inicioSemana = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        data.put("ventasSemana", ventasRepository.countByFechaBetween(inicioSemana, finDia));
        // Ventas del mes
        LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        data.put("ventasMes", ventasRepository.countByFechaBetween(inicioMes, finDia));
        return data;
    }
}
