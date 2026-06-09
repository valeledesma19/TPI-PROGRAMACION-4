package com.prode.tpi.feature.fecha.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prode.tpi.feature.fecha.model.EstadoFecha;
import com.prode.tpi.feature.fecha.model.Fecha;

public interface FechaRepository extends JpaRepository<Fecha, Long> {

    List<Fecha> findByEstado(EstadoFecha estado);
     
    boolean existsByNombre(String nombre);
    Optional<Fecha> findByNombre(String nombre);

}