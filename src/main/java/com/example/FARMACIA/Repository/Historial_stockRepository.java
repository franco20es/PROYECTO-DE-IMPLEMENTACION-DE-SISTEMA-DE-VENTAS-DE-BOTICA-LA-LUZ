package com.example.FARMACIA.Repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FARMACIA.Model.Historial_stock;

public interface  Historial_stockRepository extends JpaRepository<Historial_stock, Long> {
    List<Historial_stock> findByProductoId(Long productoId);
    List<Historial_stock> findByProductoIdOrderByFechaDesc(Long productoId);
}
