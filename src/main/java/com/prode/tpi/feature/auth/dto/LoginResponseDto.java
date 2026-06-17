package com.prode.tpi.feature.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private Long idUsuario;
    private String email;
    private String rol;
}