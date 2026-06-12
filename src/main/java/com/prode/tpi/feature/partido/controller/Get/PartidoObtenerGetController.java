package com.prode.tpi.feature.partido.controller.Get;

import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoObtenerGetController {

    private final PartidoService partidoService;

    /**
     * GET /api/partidos/{id}
     * RF4.4 - Obtiene un partido específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponseDto> obtenerPartidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(partidoService.obtenerPartidoPorId(id));
    }

}