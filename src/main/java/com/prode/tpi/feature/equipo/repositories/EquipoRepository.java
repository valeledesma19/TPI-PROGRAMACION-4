package com.prode.tpi.feature.equipo.repositories;

import com.prode.tpi.feature.equipo.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}