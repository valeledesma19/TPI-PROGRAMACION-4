package com.prode.tpi.feature.pronostico.model;
import com.prode.tpi.feature.partido.model.Partido;
import com.prode.tpi.feature.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "pronosticos",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "id_usuario",
                                "id_partido"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pronostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPronostico;

    private Integer golesLocalPredicho;

    private Integer golesVisitantePredicho;

    private Integer puntosObtenidos;

    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_partido")
    private Partido partido;
}
