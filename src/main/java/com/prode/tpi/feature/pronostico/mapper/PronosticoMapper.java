package com.prode.tpi.feature.pronostico.mapper;

import com.prode.tpi.feature.pronostico.dto.PronosticoResponseDto;
import com.prode.tpi.feature.pronostico.model.Pronostico;

public class PronosticoMapper {

    public static PronosticoResponseDto toDto(Pronostico p) {
        return PronosticoResponseDto.builder()
                .idPronostico(p.getIdPronostico())
                .usuarioId(p.getUsuario().getIdUsuario())
                .partidoId(p.getPartido().getIdPartido())
                .golesLocalPredicho(p.getGolesLocalPredicho())
                .golesVisitantePredicho(p.getGolesVisitantePredicho())
                .puntosObtenidos(p.getPuntosObtenidos())
                .fechaCreacion(p.getFechaCreacion())
                .build();
    }
}