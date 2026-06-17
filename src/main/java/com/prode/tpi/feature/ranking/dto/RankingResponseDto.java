package com.prode.tpi.feature.ranking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingResponseDto {

    private Integer posicion;

    private Long usuarioId;

    private String nombre;

    private String email;

    private Integer puntosTotales;

    private Long plenos;

    private LocalDateTime fechaPrimerPronostico;
}