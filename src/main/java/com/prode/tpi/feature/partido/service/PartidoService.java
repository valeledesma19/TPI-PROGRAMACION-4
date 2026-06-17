package com.prode.tpi.feature.partido.service;

import com.prode.tpi.feature.partido.dto.PartidoRequestDto;
import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.dto.PartidoUpdateRequestDto;
import com.prode.tpi.feature.partido.dto.ResultadoPartidoRequestDto;
import java.util.List;

public interface PartidoService {

    // RF4.1
    PartidoResponseDto crearPartido(PartidoRequestDto request);

    // RF4.2
    PartidoResponseDto modificarPartido(Long id, PartidoUpdateRequestDto request);

    // RF4.3
    PartidoResponseDto pasarAEnJuego(Long id);

    // RF4.4
    List<PartidoResponseDto> listarPartidos(Long idFecha);

    PartidoResponseDto obtenerPartidoPorId(Long id);

    PartidoResponseDto cargarResultado(Long id, ResultadoPartidoRequestDto request);

    // RF4.5
    void eliminarPartido(Long id);



}
