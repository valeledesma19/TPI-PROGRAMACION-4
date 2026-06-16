package com.prode.tpi.feature.fecha.Controller.Delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prode.tpi.feature.fecha.Services.FechaService;

@RestController
@RequestMapping("api/Fecha")
public class EliminarFechaController {

    @Autowired
    private FechaService fechaService;

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<?> eliminarFecha(@PathVariable Long id) {
        try {
            fechaService.eliminarFecha(id);
            return ResponseEntity.noContent().build();
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