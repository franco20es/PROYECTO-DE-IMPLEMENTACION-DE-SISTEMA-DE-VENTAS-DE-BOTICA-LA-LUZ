package com.example.FARMACIA.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Detalle_ventas;

public interface  Detalle_ventasRepository extends JpaRepository<Detalle_ventas, Long> {
    List<Detalle_ventas> findByVentaId(Long idVenta);

    // Consulta paginada y filtrada
    Page<Detalle_ventas> findByVenta_Cliente_NombreContainingIgnoreCase(
        String cliente,
        Pageable pageable
    );
    // Alternativa: si fechaHora es tipo LocalDateTime, se puede ajustar el filtro
}
