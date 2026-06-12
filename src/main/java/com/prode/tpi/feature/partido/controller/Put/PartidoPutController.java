package com.prode.tpi.feature.partido.controller.Put;

import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.dto.PartidoUpdateRequestDto;
import com.prode.tpi.feature.partido.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoPutController {

    private final PartidoService partidoService;

    /**
     * PUT /api/partidos/{id}
     * RF4.2 - Modifica horario o equipos de un partido en estado POR_JUGARSE.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartidoResponseDto> modificarPartido(
            @PathVariable Long id,
            @Valid @RequestBody PartidoUpdateRequestDto request) {
        return ResponseEntity.ok(partidoService.modificarPartido(id, request));
    }

}
