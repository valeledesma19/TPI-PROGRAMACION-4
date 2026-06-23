package com.prode.tpi.feature.partido.repositories;

import com.prode.tpi.feature.partido.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByFecha_IdFechaOrderByFechaHoraInicioAsc(Long idFecha);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Partido p
            WHERE p.fecha.idFecha = :idFecha
            AND (
                (p.equipoLocal.idEquipo = :idEquipoLocal AND p.equipoVisitante.idEquipo = :idEquipoVisitante)
                OR
                (p.equipoLocal.idEquipo = :idEquipoVisitante AND p.equipoVisitante.idEquipo = :idEquipoLocal)
            )
            """)
    boolean existePartidoIgualEnFecha(
            @Param("idFecha") Long idFecha,
            @Param("idEquipoLocal") Long idEquipoLocal,
            @Param("idEquipoVisitante") Long idEquipoVisitante
    );

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Partido p
            WHERE p.fecha.idFecha = :idFecha
            AND p.idPartido <> :idPartido
            AND (
                (p.equipoLocal.idEquipo = :idEquipoLocal AND p.equipoVisitante.idEquipo = :idEquipoVisitante)
                OR
                (p.equipoLocal.idEquipo = :idEquipoVisitante AND p.equipoVisitante.idEquipo = :idEquipoLocal)
            )
            """)
    boolean existePartidoIgualEnFechaExcluyendoPartido(
            @Param("idFecha") Long idFecha,
            @Param("idPartido") Long idPartido,
            @Param("idEquipoLocal") Long idEquipoLocal,
            @Param("idEquipoVisitante") Long idEquipoVisitante
    );
}