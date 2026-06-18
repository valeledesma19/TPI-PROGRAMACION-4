package com.prode.tpi.feature.estadisticas.controller.get;

import com.prode.tpi.feature.estadisticas.dto.EstadisticasResponseDto;
import com.prode.tpi.feature.estadisticas.service.EstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticasGetController {

    private final EstadisticasService estadisticasService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<EstadisticasResponseDto> misEstadisticas(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(estadisticasService.obtenerEstadisticas(usuarioId));
    }
}