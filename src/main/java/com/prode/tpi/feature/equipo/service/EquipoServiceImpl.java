package com.prode.tpi.feature.equipo.service;
 
import com.prode.tpi.feature.equipo.dto.EquipoRequestDto;
import com.prode.tpi.feature.equipo.dto.EquipoResponseDto;
import com.prode.tpi.feature.equipo.model.Equipo;
import com.prode.tpi.feature.equipo.repositories.EquipoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {
 
    private final EquipoRepository equipoRepository;
 
    // ── RF2.1 Registro ────────────────────────────────────────────────────────
 
    @Override
    @Transactional
    public EquipoResponseDto crearEquipo(EquipoRequestDto request) {
        if (equipoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un equipo con el nombre: " + request.getNombre());
        }
 
        Equipo equipo = Equipo.builder()
                .nombre(request.getNombre())
                .build();
 
        Equipo guardado = equipoRepository.save(equipo);
        return toResponseDto(guardado);
    }
 
    // ── RF2.2 Consulta ────────────────────────────────────────────────────────
 
    @Override
    @Transactional(readOnly = true)
    public List<EquipoResponseDto> listarEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<EquipoResponseDto> buscarEquiposPorNombre(String nombre) {
        return equipoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public EquipoResponseDto obtenerEquipoPorId(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Equipo no encontrado con id: " + id));
        return toResponseDto(equipo);
    }
 
    // ── RF2.3 Eliminación ─────────────────────────────────────────────────────
 
    @Override
    @Transactional
    public void eliminarEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Equipo no encontrado con id: " + id));
        try {
            equipoRepository.delete(equipo);
            equipoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el equipo porque está asociado a uno o más partidos.");
        }
    }
 
    // ── Mapper ────────────────────────────────────────────────────────────────
 
    private EquipoResponseDto toResponseDto(Equipo equipo) {
        return EquipoResponseDto.builder()
                .idEquipo(equipo.getIdEquipo())
                .nombre(equipo.getNombre())
                .build();
    }
 
}
 