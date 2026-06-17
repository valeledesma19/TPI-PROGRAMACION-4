package com.prode.tpi.feature.auth.controller;

import com.prode.tpi.feature.auth.dto.*;
import com.prode.tpi.feature.auth.service.AuthService;
import com.prode.tpi.feature.usuario.model.Rol;
import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody RegisterRequest dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("/create-admin")
    public ResponseEntity<Usuario> createAdmin(@RequestBody RegisterRequest dto) {

        Usuario admin = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(Rol.ADMIN)
                .fechaRegistro(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(usuarioRepository.save(admin));
    }
}