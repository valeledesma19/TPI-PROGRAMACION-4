package com.prode.tpi.feature.equipo.dto;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoRequestDto {
 
    @NotBlank(message = "El nombre del equipo no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;
 
}