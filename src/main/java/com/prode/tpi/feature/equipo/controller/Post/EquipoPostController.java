package com.prode.tpi.feature.equipo.controller.Post;
 
import com.prode.tpi.feature.equipo.dto.EquipoRequestDto;
import com.prode.tpi.feature.equipo.dto.EquipoResponseDto;
import com.prode.tpi.feature.equipo.service.EquipoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoPostController {
 
    private final EquipoService equipoService;
 
    /**
     * POST /api/equipos
     * RF2.1 - Registra un nuevo equipo en el sistema.
     */
    @PostMapping
    public ResponseEntity<EquipoResponseDto> crearEquipo(
            @Valid @RequestBody EquipoRequestDto request) {
        EquipoResponseDto response = equipoService.crearEquipo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
 
}
 
