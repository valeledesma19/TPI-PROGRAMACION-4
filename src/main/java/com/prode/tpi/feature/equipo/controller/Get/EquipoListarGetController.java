package com.prode.tpi.feature.equipo.controller.Get;
 
import com.prode.tpi.feature.equipo.dto.EquipoResponseDto;
import com.prode.tpi.feature.equipo.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoListarGetController {
 
    private final EquipoService equipoService;
 
    /**
     * GET /api/equipos
     * RF2.2 - Lista todos los equipos. Acepta ?nombre= para filtrar.
     */
    @GetMapping
    public ResponseEntity<List<EquipoResponseDto>> listarEquipos(
            @RequestParam(required = false) String nombre) {
        List<EquipoResponseDto> equipos = (nombre != null && !nombre.isBlank())
                ? equipoService.buscarEquiposPorNombre(nombre)
                : equipoService.listarEquipos();
        return ResponseEntity.ok(equipos);
    }
 
}
