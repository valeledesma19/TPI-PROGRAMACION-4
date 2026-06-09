package com.prode.tpi.feature.equipo.controller.Get;
 
import com.prode.tpi.feature.equipo.dto.EquipoResponseDto;
import com.prode.tpi.feature.equipo.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoObtenerGetController {
 
    private final EquipoService equipoService;
 
    /**
     * GET /api/equipos/{id}
     * RF2.2 - Obtiene un equipo específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDto> obtenerEquipoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.obtenerEquipoPorId(id));
    }
 
}
