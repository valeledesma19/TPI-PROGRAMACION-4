package com.prode.tpi.feature.fecha.Dtos.Response;

import java.util.List;

import com.prode.tpi.feature.fecha.model.EstadoFecha;
import com.prode.tpi.feature.partido.model.Partido;

import lombok.Data;

@Data
public class CrearFechaResponseDto {
    
    private long idFecha;
    private String nombre;
    private EstadoFecha estado;
    private List<Partido> partidos;

}
