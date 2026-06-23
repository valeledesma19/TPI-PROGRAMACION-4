package com.prode.tpi.feature.pronostico.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PronosticoResponseDto {

    private Long idPronostico;

    private Long usuarioId;
    private String nombreUsuario;

    private Long partidoId;

    private Integer golesLocalPredicho;
    private Integer golesVisitantePredicho;

    private Integer puntosObtenidos;

    private LocalDateTime fechaCreacion;
}