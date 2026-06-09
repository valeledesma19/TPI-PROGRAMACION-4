package com.prode.tpi.feature.partido.model;


import com.prode.tpi.feature.equipo.model.Equipo;
import com.prode.tpi.feature.fecha.model.Fecha;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "partidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartido;

    private LocalDateTime fechaHoraInicio;

    @Enumerated(EnumType.STRING)
    private EstadoPartido estado;

    private Integer golesLocal;

    private Integer golesVisitante;

    @Enumerated(EnumType.STRING)
    private TendenciaResultado tendenciaResultado;

    @ManyToOne
    @JoinColumn(name = "id_fecha")
    private Fecha fecha;

    @ManyToOne
    @JoinColumn(name = "id_equipo_local")
    private Equipo equipoLocal;

    @ManyToOne
    @JoinColumn(name = "id_equipo_visitante")
    private Equipo equipoVisitante;

    @OneToMany(mappedBy = "partido")
    private List<Pronostico> pronosticos;
}