package com.prode.tpi.feature.partido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidoRequestDto {

    @NotNull(message = "La fecha contenedora es obligatoria")
    private Long idFecha;

    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "El equipo local es obligatorio")
    private Long idEquipoLocal;

    @NotNull(message = "El equipo visitante es obligatorio")
    private Long idEquipoVisitante;

}
