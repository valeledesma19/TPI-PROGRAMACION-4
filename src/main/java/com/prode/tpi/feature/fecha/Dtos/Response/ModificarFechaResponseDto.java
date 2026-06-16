package com.prode.tpi.feature.fecha.Dtos.Response;

import com.prode.tpi.feature.fecha.model.EstadoFecha;
import lombok.Data;

@Data
public class ModificarFechaResponseDto {
    private Long idFecha;
    private String nombre;
    private EstadoFecha estado;
}