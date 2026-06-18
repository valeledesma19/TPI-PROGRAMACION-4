package com.prode.tpi.feature.estadisticas.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticasResponseDto {

    private Long usuarioId;
    private String nombre;

    private int totalPronosticos;
    private int plenos;
    private int aciertos;
    private int incorrectos;
    private int pendientes;
    private int puntosTotales;

    private double efectividad;
}