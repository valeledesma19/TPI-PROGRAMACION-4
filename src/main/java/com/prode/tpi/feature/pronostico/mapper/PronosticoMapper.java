package com.prode.tpi.feature.pronostico.mapper;

import com.prode.tpi.feature.pronostico.dto.PronosticoResponseDto;
import com.prode.tpi.feature.pronostico.model.Pronostico;

public class PronosticoMapper {

    public static PronosticoResponseDto toDto(Pronostico pronostico) {

        return PronosticoResponseDto.builder()
                .idPronostico(pronostico.getIdPronostico())
                .usuarioId(pronostico.getUsuario().getIdUsuario())
                .nombreUsuario(pronostico.getUsuario().getNombre())
                .partidoId(pronostico.getPartido().getIdPartido())
                .golesLocalPredicho(pronostico.getGolesLocalPredicho())
                .golesVisitantePredicho(pronostico.getGolesVisitantePredicho())
                .puntosObtenidos(pronostico.getPuntosObtenidos())
                .fechaCreacion(pronostico.getFechaCreacion())
                .build();
    }
}