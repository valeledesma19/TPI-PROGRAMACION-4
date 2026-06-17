package com.prode.tpi.feature.partido.controller.Put;

import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.dto.ResultadoPartidoRequestDto;
import com.prode.tpi.feature.partido.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoResultadoPutController {

    private final PartidoService partidoService;

    @PutMapping("/{id}/resultado")
    public ResponseEntity<PartidoResponseDto> cargarResultado(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoPartidoRequestDto request
    ) {
        return ResponseEntity.ok(partidoService.cargarResultado(id, request));
    }
}