package com.prode.tpi.feature.pronostico.controller.post;

import com.prode.tpi.feature.pronostico.dto.PronosticoRequestDto;
import com.prode.tpi.feature.pronostico.dto.PronosticoResponseDto;
import com.prode.tpi.feature.pronostico.mapper.PronosticoMapper;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.service.PronosticoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pronosticos")
@RequiredArgsConstructor
public class PronosticoPostController {

    private final PronosticoService pronosticoService;

    /**
     * RF5.1 - Crear o actualizar pronóstico
     */
    @PostMapping("/{usuarioId}")
    public ResponseEntity<PronosticoResponseDto> crearOActualizar(
            @PathVariable Long usuarioId,
            @Valid @RequestBody PronosticoRequestDto request
    ) {

        Pronostico pronostico = pronosticoService.crearOActualizarPronostico(
                usuarioId,
                request.getPartidoId(),
                request.getGolesLocalPredicho(),
                request.getGolesVisitantePredicho()
        );

        return ResponseEntity.ok(PronosticoMapper.toDto(pronostico));
    }
}