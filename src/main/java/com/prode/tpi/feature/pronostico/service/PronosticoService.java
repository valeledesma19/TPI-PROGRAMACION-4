package com.prode.tpi.feature.pronostico.service;

import com.prode.tpi.feature.pronostico.dto.PronosticoGroupedResponseDto;
import com.prode.tpi.feature.pronostico.model.Pronostico;

import java.util.List;

public interface PronosticoService {

    Pronostico crearOActualizarPronostico(
            Long usuarioId,
            Long partidoId,
            Integer golesLocal,
            Integer golesVisitante
    );
    List<Pronostico> verPronosticosDeTerceros(Long partidoId);
    PronosticoGroupedResponseDto obtenerPronosticosAgrupados(Long usuarioId);
}