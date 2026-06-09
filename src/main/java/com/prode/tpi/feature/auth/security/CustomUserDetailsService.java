package com.prode.tpi.feature.auth.security;

import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRol().name())
                .build();
    }
}