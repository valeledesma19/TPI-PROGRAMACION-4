package com.prode.tpi.feature.partido.controller.Patch;

import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoPatchController {

    private final PartidoService partidoService;

    /**
     * PATCH /api/partidos/{id}/en-juego
     * RF4.3 - Cambia el estado del partido de POR_JUGARSE a EN_JUEGO.
     */
    @PatchMapping("/{id}/en-juego")
    public ResponseEntity<PartidoResponseDto> pasarAEnJuego(@PathVariable Long id) {
        return ResponseEntity.ok(partidoService.pasarAEnJuego(id));
    }

}
