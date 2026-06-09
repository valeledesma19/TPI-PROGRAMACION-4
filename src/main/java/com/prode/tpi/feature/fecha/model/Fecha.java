package com.prode.tpi.feature.fecha.model;

import java.util.List;

import com.prode.tpi.feature.partido.model.Partido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fechas")
@Data
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