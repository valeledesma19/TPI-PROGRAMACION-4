package com.prode.tpi.feature.equipo.controller.Delete;
 
import com.prode.tpi.feature.equipo.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoDeleteController {
 
    private final EquipoService equipoService;
 
    /**
     * DELETE /api/equipos/{id}
     * RF2.3 - Elimina un equipo siempre que no esté asociado a ningún partido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
 
}
