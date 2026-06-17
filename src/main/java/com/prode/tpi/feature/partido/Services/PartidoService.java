package com.prode.tpi.feature.partido.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prode.tpi.feature.fecha.Services.FechaService;
import com.prode.tpi.feature.fecha.model.Fecha;
import com.prode.tpi.feature.partido.Dtos.Request.ActualizarEstadoPartidoRequestDto;
import com.prode.tpi.feature.partido.Dtos.Response.ActualizarEstadoPartidoResponseDto;
import com.prode.tpi.feature.partido.model.Partido;
import com.prode.tpi.feature.partido.repositories.PartidoRepository;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private FechaService fechaService;

    public ActualizarEstadoPartidoResponseDto actualizarEstado(Long id, ActualizarEstadoPartidoRequestDto dto) {

        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        partido.setEstado(dto.getEstado());
        Partido partidoActualizado = partidoRepository.save(partido);

        Fecha fecha = partidoActualizado.getFecha();
        fechaService.actualizarEstadoSegunPartidos(fecha);

        ActualizarEstadoPartidoResponseDto respuesta = new ActualizarEstadoPartidoResponseDto();
        respuesta.setIdPartido(partidoActualizado.getIdPartido());
        respuesta.setEstado(partidoActualizado.getEstado());
        respuesta.setIdFecha(fecha.getIdFecha());
        respuesta.setEstadoFecha(fecha.getEstado());
        return respuesta;
    }
}