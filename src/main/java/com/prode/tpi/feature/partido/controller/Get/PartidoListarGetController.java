package com.prode.tpi.feature.partido.controller.Get;

import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoListarGetController {

    private final PartidoService partidoService;

    /**
     * GET /api/partidos
     * RF4.4 - Lista todos los partidos. Acepta ?idFecha= para filtrar.
     */
    @GetMapping
    public ResponseEntity<List<PartidoResponseDto>> listarPartidos(
            @RequestParam(required = false) Long idFecha) {
        return ResponseEntity.ok(partidoService.listarPartidos(idFecha));
    }

}
