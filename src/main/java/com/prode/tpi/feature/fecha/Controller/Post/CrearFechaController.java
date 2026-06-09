package com.prode.tpi.feature.fecha.Controller.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prode.tpi.feature.fecha.Dtos.Request.CrearFechaRequestDto;
import com.prode.tpi.feature.fecha.Dtos.Response.CrearFechaResponseDto;
import com.prode.tpi.feature.fecha.Services.FechaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/Fecha")
public class CrearFechaController {

    @Autowired
    private FechaService fechaService;

    @PostMapping("/Crear")
    public ResponseEntity<?> CrearFecha(@Valid @RequestBody CrearFechaRequestDto CFR) {
        try {
            CrearFechaResponseDto respuesta = fechaService.CrearFecha(CFR);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
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

        public String getMensaje() {
            return mensaje;
        }

        public int getCodigo() {
            return codigo;
        }
    }

}


