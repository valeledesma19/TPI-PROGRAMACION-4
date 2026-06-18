package com.prode.tpi.feature.estadisticas.service;

import com.prode.tpi.feature.estadisticas.dto.EstadisticasResponseDto;

public interface EstadisticasService {
    EstadisticasResponseDto obtenerEstadisticas(Long usuarioId);
}