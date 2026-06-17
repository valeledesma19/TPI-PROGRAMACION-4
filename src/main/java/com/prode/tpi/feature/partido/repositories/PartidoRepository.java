package com.prode.tpi.feature.partido.repositories;
 
import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.partido.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
 
@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
 
    List<Partido> findByFecha_IdFechaOrderByFechaHoraInicioAsc(Long idFecha);
 
    boolean existsByFecha_IdFechaAndEstado(Long idFecha, EstadoPartido estado);
 
}