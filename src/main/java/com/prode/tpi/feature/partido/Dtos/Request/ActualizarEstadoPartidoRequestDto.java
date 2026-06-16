package com.prode.tpi.feature.partido.Dtos.Request;

import com.prode.tpi.feature.partido.model.EstadoPartido;
import lombok.Data;

@Data
public class ActualizarEstadoPartidoRequestDto {
    private EstadoPartido estado;
}