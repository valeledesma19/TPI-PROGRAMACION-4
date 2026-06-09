package com.prode.tpi.feature.fecha.model;

import com.prode.tpi.feature.partido.model.Partido;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fechas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFecha;

    @Column(unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private EstadoFecha estado;

    @OneToMany(mappedBy = "fecha")
    private List<Partido> partidos;
}