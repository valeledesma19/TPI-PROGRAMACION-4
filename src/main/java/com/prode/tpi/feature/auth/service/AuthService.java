package com.prode.tpi.feature.auth.service;

import com.prode.tpi.feature.auth.dto.LoginRequest;
import com.prode.tpi.feature.auth.dto.LoginResponseDto;
import com.prode.tpi.feature.auth.dto.RegisterRequest;
import com.prode.tpi.feature.auth.security.JwtService;
import com.prode.tpi.feature.usuario.model.Rol;
import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //  RF1.1 REGISTRO
    public Usuario register(RegisterRequest dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(Rol.USER)
                .fechaRegistro(LocalDateTime.now())
                .build();

        return usuarioRepository.save(usuario);
    }

    //  RF1.2 LOGIN
    public LoginResponseDto login(LoginRequest dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol().name()
        );

        return new LoginResponseDto(
                token,
                usuario.getIdUsuario(),
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }
}