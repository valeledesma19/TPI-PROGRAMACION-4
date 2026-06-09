package com.prode.tpi.feature.pronostico.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PronosticoGroupedResponseDto {

    private List<PronosticoResponseDto> porJugarse;
    private List<PronosticoResponseDto> enJuego;
    private List<PronosticoResponseDto> finalizado;
}