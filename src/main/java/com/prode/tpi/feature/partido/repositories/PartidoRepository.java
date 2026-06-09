package com.prode.tpi.feature.partido.repositories;

import com.prode.tpi.feature.fecha.model.Fecha;
import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.partido.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByFechaOrderByFechaHoraInicioAsc(Fecha fecha);

    List<Partido> findByEstado(EstadoPartido estado);

}