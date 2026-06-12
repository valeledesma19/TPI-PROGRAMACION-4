package com.prode.tpi.feature.partido.controller.Post;

import com.prode.tpi.feature.partido.dto.PartidoRequestDto;
import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoPostController {

    private final PartidoService partidoService;

    /**
     * POST /api/partidos
     * RF4.1 - Registra un nuevo partido dentro de una Fecha.
     */
    @PostMapping
    public ResponseEntity<PartidoResponseDto> crearPartido(
            @Valid @RequestBody PartidoRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partidoService.crearPartido(request));
    }

}