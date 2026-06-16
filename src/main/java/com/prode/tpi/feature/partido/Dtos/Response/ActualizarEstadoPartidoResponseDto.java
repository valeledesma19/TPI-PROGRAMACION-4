package com.prode.tpi.feature.partido.Dtos.Response;

import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.fecha.model.EstadoFecha;
import lombok.Data;

@Data
public class ActualizarEstadoPartidoResponseDto {
    private Long idPartido;
    private EstadoPartido estado;
    private Long idFecha;
    private EstadoFecha estadoFecha;
}