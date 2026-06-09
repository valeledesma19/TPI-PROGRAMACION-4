package com.prode.tpi.feature.pronostico.repositories;

import com.prode.tpi.feature.partido.model.Partido;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PronosticoRepository extends JpaRepository<Pronostico, Long> {

    Optional<Pronostico> findByUsuarioAndPartido(
            Usuario usuario,
            Partido partido
    );

    List<Pronostico> findByUsuario(Usuario usuario);

    List<Pronostico> findByPartido(Partido partido);

}