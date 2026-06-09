package com.prode.tpi.feature.pronostico.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PronosticoRequestDto {

    @NotNull(message = "El id del partido es obligatorio")
    private Long partidoId;

    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesLocalPredicho;

    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesVisitantePredicho;
}