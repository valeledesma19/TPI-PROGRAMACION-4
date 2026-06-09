package com.prode.tpi.feature.fecha.repositories;

import com.prode.tpi.feature.fecha.model.EstadoFecha;
import com.prode.tpi.feature.fecha.model.Fecha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FechaRepository extends JpaRepository<Fecha, Long> {

    List<Fecha> findByEstado(EstadoFecha estado);

}