package com.prode.tpi.feature.pronostico.controller.get;

import com.prode.tpi.feature.pronostico.dto.PronosticoGroupedResponseDto;
import com.prode.tpi.feature.pronostico.dto.PronosticoResponseDto;
import com.prode.tpi.feature.pronostico.mapper.PronosticoMapper;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import com.prode.tpi.feature.pronostico.service.PronosticoService;
import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pronosticos")
@RequiredArgsConstructor
public class PronosticoGetController {

    private final PronosticoRepository pronosticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PronosticoService pronosticoService;

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
}