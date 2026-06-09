package com.prode.tpi.feature.equipo.dto;
 
import lombok.*;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoResponseDto {
 
    private Long idEquipo;
    private String nombre;
 
}