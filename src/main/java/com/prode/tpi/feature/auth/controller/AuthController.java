package com.prode.tpi.feature.auth.controller;

import com.prode.tpi.feature.auth.dto.*;
import com.prode.tpi.feature.auth.service.AuthService;
import com.prode.tpi.feature.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody RegisterRequest dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}