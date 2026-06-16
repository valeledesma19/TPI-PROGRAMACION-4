package com.prode.tpi.feature.fecha.Controller.Get;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prode.tpi.feature.fecha.Dtos.Response.FechaResponseDto;
import com.prode.tpi.feature.fecha.Services.FechaService;
import com.prode.tpi.feature.fecha.model.EstadoFecha;

@RestController
@RequestMapping("api/Fecha")
public class ListarFechaController {

    @Autowired
    private FechaService fechaService;

    @GetMapping("/Listar")
    public ResponseEntity<?> listarFechas(@RequestParam(required = false) EstadoFecha estado) {
        try {
            List<FechaResponseDto> respuesta = fechaService.listarFechas(estado);
            return ResponseEntity.ok(respuesta);
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