package com.prode.tpi.feature.equipo.service;
 
import com.prode.tpi.feature.equipo.dto.EquipoRequestDto;
import com.prode.tpi.feature.equipo.dto.EquipoResponseDto;
 
import java.util.List;
 
public interface EquipoService {
 
    EquipoResponseDto crearEquipo(EquipoRequestDto request);
 
    List<EquipoResponseDto> listarEquipos();
 
    List<EquipoResponseDto> buscarEquiposPorNombre(String nombre);
 
    EquipoResponseDto obtenerEquipoPorId(Long id);
 
    void eliminarEquipo(Long id);
 
}
