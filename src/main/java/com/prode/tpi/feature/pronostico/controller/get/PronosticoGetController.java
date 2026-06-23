package com.prode.tpi.feature.pronostico.controller.get;

import com.prode.tpi.feature.partido.model.Partido;
import com.prode.tpi.feature.partido.repositories.PartidoRepository;
import com.prode.tpi.feature.pronostico.dto.PronosticoGroupedResponseDto;
import com.prode.tpi.feature.pronostico.dto.PronosticoResponseDto;
import com.prode.tpi.feature.pronostico.mapper.PronosticoMapper;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import com.prode.tpi.feature.pronostico.service.PronosticoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pronosticos")
@RequiredArgsConstructor
public class PronosticoGetController {

    private final PronosticoRepository pronosticoRepository;
    private final PronosticoService pronosticoService;
    private final PartidoRepository partidoRepository;

    /**
     * RF5.2 - Mis pronósticos
     */
    @GetMapping("/usuario/{usuarioId}/agrupados")
    public ResponseEntity<PronosticoGroupedResponseDto> misPronosticosAgrupados(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                pronosticoService.obtenerPronosticosAgrupados(usuarioId)
        );
    }

    /**
     * RF5.3 - Pronósticos de terceros para usuarios.
     * Respeta el bloqueo de privacidad.
     */
    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<List<PronosticoResponseDto>> verPronosticosDeTerceros(
            @PathVariable Long partidoId
    ) {
        List<Pronostico> pronosticos =
                pronosticoService.verPronosticosDeTerceros(partidoId);

        return ResponseEntity.ok(
                pronosticos.stream()
                        .map(PronosticoMapper::toDto)
                        .toList()
        );
    }

    /**
     * Vista ADMIN - Permite ver todos los pronósticos de un partido.
     * No usa la regla de bloqueo porque es una función administrativa.
     */
    @GetMapping("/admin/partido/{partidoId}")
    public ResponseEntity<List<PronosticoResponseDto>> verPronosticosPorPartidoAdmin(
            @PathVariable Long partidoId
    ) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado"));

        List<Pronostico> pronosticos = pronosticoRepository.findByPartido(partido);

        return ResponseEntity.ok(
                pronosticos.stream()
                        .map(PronosticoMapper::toDto)
                        .toList()
        );
    }
}