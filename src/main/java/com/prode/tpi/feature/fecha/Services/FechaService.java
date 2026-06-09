package com.prode.tpi.feature.fecha.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prode.tpi.feature.fecha.Dtos.Request.CrearFechaRequestDto;
import com.prode.tpi.feature.fecha.Dtos.Response.CrearFechaResponseDto;
import com.prode.tpi.feature.fecha.model.Fecha;
import com.prode.tpi.feature.fecha.repositories.FechaRepository;

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


}
