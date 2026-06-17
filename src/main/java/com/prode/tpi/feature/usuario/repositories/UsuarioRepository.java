package com.prode.tpi.feature.usuario.repositories;

import com.prode.tpi.feature.usuario.model.Rol;
import com.prode.tpi.feature.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByRol(Rol rol);
}