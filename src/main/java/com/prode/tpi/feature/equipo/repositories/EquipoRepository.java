package com.prode.tpi.feature.equipo.repositories;
 
import com.prode.tpi.feature.equipo.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
import java.util.Optional;
 
@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
 
    Optional<Equipo> findByNombreIgnoreCase(String nombre);
 
    List<Equipo> findByNombreContainingIgnoreCase(String nombre);
 
    boolean existsByNombreIgnoreCase(String nombre);
 
}