package com.prode.tpi.feature.partido.dto;

import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.partido.model.TendenciaResultado;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidoResponseDto {

    private Long idPartido;
    private LocalDateTime fechaHoraInicio;
    private EstadoPartido estado;

    private Integer golesLocal;
    private Integer golesVisitante;
    private TendenciaResultado tendenciaResultado;

    private Long idFecha;
    private String nombreFecha;

    private Long idEquipoLocal;
    private String nombreEquipoLocal;

    private Long idEquipoVisitante;
    private String nombreEquipoVisitante;
}