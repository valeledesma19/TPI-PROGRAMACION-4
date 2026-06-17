package com.prode.tpi.feature.partido.controller.Delete;

import com.prode.tpi.feature.partido.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoDeleteController {

    private final PartidoService partidoService;

    /**
     * DELETE /api/partidos/{id}
     * RF4.5 - Elimina un partido en estado POR_JUGARSE sin pronósticos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPartido(@PathVariable Long id) {
        partidoService.eliminarPartido(id);
        return ResponseEntity.noContent().build();
    }

}