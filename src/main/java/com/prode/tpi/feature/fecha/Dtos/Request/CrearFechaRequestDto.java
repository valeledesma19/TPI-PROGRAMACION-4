package com.prode.tpi.feature.fecha.Dtos.Request;

import com.prode.tpi.feature.fecha.model.EstadoFecha;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearFechaRequestDto {
    
    @NotBlank(message="El nombre es requerido")
    @Pattern(regexp="^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$", message= "El nombre no puede contener numeros")
    private String nombre;

    @NotNull(message="El estado no puede quedar vacio")
    private EstadoFecha estadoFecha;
}
