package com.prode.tpi.feature.partido.Controller.Put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prode.tpi.feature.partido.Dtos.Request.ActualizarEstadoPartidoRequestDto;
import com.prode.tpi.feature.partido.Dtos.Response.ActualizarEstadoPartidoResponseDto;
import com.prode.tpi.feature.partido.Services.PartidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/Partido")
public class ActualizarEstadoPartidoController {

    @Autowired
    private PartidoService partidoService;

    @PutMapping("/ActualizarEstado/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @Valid @RequestBody ActualizarEstadoPartidoRequestDto dto) {
        try {
            ActualizarEstadoPartidoResponseDto respuesta = partidoService.actualizarEstado(id, dto);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    public static class ErrorResponseDto {
        public String mensaje;
        public int codigo;

        public ErrorResponseDto(String mensaje, int codigo) {
            this.mensaje = mensaje;
            this.codigo = codigo;
        }

        public String getMensaje() { return mensaje; }
        public int getCodigo() { return codigo; }
    }
}