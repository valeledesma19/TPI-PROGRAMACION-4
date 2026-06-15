package com.prode.tpi.feature.fecha.Services;

import com.prode.tpi.feature.fecha.Dtos.Request.ModificarFechaRequestDto;
import com.prode.tpi.feature.fecha.Dtos.Response.FechaResponseDto;
import com.prode.tpi.feature.fecha.Dtos.Response.ModificarFechaResponseDto;
import com.prode.tpi.feature.fecha.model.EstadoFecha;
import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.partido.model.Partido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prode.tpi.feature.fecha.Dtos.Request.CrearFechaRequestDto;
import com.prode.tpi.feature.fecha.Dtos.Response.CrearFechaResponseDto;
import com.prode.tpi.feature.fecha.model.Fecha;
import com.prode.tpi.feature.fecha.repositories.FechaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FechaService {

    @Autowired
    private FechaRepository fechaRepository;
 
    public CrearFechaResponseDto CrearFecha(CrearFechaRequestDto fechaDto) {
        // Comprobacion para saber si en la bd esta guardado una fecha con ese nombre
        if (fechaRepository.existsByNombre(fechaDto.getNombre())) {
            throw new RuntimeException("El nombre del equipo ya  está registrado en el sistema");
        }
        // Creo un objeto Fecha para luego asignarle los atributos que se pasan del Rquest
        Fecha nuevaFecha = new Fecha();
        nuevaFecha.setNombre(fechaDto.getNombre());
        nuevaFecha.setEstado(fechaDto.getEstadoFecha());

        // Guardo la fecha creada en la Base de datos
        Fecha fechaGuardar = fechaRepository.save(nuevaFecha);

        // Genero la respuesta, tiene que ser del tipo ResponseDto
        CrearFechaResponseDto respuesta = new CrearFechaResponseDto();
        respuesta.setIdFecha(fechaGuardar.getIdFecha());
        respuesta.setNombre(fechaGuardar.getNombre());
        respuesta.setEstado(fechaGuardar.getEstado());
        return respuesta;
    }

    public ModificarFechaResponseDto modificarFecha(Long id, ModificarFechaRequestDto dto) {

        Fecha fecha = fechaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fecha no encontrada"));

        if (fecha.getEstado() != EstadoFecha.PROGRAMADA) {
            throw new RuntimeException("Solo se pueden modificar fechas en estado Programada");
        }

        if (fecha.getPartidos() != null && !fecha.getPartidos().isEmpty()) {
            throw new RuntimeException("No se puede modificar una fecha que tiene partidos asociados");
        }

        if (!fecha.getNombre().equals(dto.getNombre()) && fechaRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("El nombre ya está registrado en el sistema");
        }

        fecha.setNombre(dto.getNombre());
        Fecha fechaActualizada = fechaRepository.save(fecha);

        ModificarFechaResponseDto respuesta = new ModificarFechaResponseDto();
        respuesta.setIdFecha(fechaActualizada.getIdFecha());
        respuesta.setNombre(fechaActualizada.getNombre());
        respuesta.setEstado(fechaActualizada.getEstado());
        return respuesta;
    }

    public void eliminarFecha(Long id) {

        Fecha fecha = fechaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fecha no encontrada"));

        if (fecha.getEstado() != EstadoFecha.PROGRAMADA) {
            throw new RuntimeException("Solo se pueden eliminar fechas en estado Programada");
        }

        if (fecha.getPartidos() != null && !fecha.getPartidos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una fecha que tiene partidos asociados");
        }

        fechaRepository.delete(fecha);
    }
    public void actualizarEstadoSegunPartidos(Fecha fecha) {
        List<Partido> partidos = fecha.getPartidos();

        if (partidos == null || partidos.isEmpty()) {
            fecha.setEstado(EstadoFecha.PROGRAMADA);
        } else if (partidos.stream().allMatch(p -> p.getEstado() == EstadoPartido.FINALIZADO)) {
            fecha.setEstado(EstadoFecha.FINALIZADA);
        } else if (partidos.stream().allMatch(p -> p.getEstado() == EstadoPartido.POR_JUGARSE)) {
            fecha.setEstado(EstadoFecha.PROGRAMADA);
        } else {
            // Al menos uno EN_JUEGO, o mezcla de POR_JUGARSE y FINALIZADO sin terminar todos
            fecha.setEstado(EstadoFecha.EN_JUEGO);
        }

        fechaRepository.save(fecha);
    }
    public List<FechaResponseDto> listarFechas(EstadoFecha estado) {

        List<Fecha> fechas;

        if (estado != null) {
            fechas = fechaRepository.findByEstado(estado);
        } else {
            fechas = fechaRepository.findAll();
        }

        return fechas.stream()
                .map(fecha -> {
                    FechaResponseDto dto = new FechaResponseDto();
                    dto.setIdFecha(fecha.getIdFecha());
                    dto.setNombre(fecha.getNombre());
                    dto.setEstado(fecha.getEstado());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
