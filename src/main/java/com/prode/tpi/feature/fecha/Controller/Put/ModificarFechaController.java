package com.prode.tpi.feature.fecha.Controller.Put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prode.tpi.feature.fecha.Dtos.Request.ModificarFechaRequestDto;
import com.prode.tpi.feature.fecha.Dtos.Response.ModificarFechaResponseDto;
import com.prode.tpi.feature.fecha.Services.FechaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/Fecha")
public class ModificarFechaController {

    @Autowired
    private FechaService fechaService;

    @PutMapping("/Modificar/{id}")
    public ResponseEntity<?> modificarFecha(@PathVariable Long id, @Valid @RequestBody ModificarFechaRequestDto dto) {
        try {
            ModificarFechaResponseDto respuesta = fechaService.modificarFecha(id, dto);
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