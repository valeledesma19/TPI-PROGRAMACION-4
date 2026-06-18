package com.prode.tpi.feature.estadisticas.service;

import com.prode.tpi.feature.estadisticas.dto.EstadisticasResponseDto;
import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadisticasServiceImpl implements EstadisticasService {

    private final UsuarioRepository usuarioRepository;
    private final PronosticoRepository pronosticoRepository;

    @Override
    @Transactional(readOnly = true)
    public EstadisticasResponseDto obtenerEstadisticas(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Pronostico> pronosticos = pronosticoRepository.findByUsuario(usuario);

        int total       = pronosticos.size();
        int puntos      = 0;
        int plenos      = 0;
        int aciertos    = 0;
        int incorrectos = 0;
        int pendientes  = 0;

        for (Pronostico p : pronosticos) {
            EstadoPartido estado = p.getPartido().getEstado();

            if (estado != EstadoPartido.FINALIZADO) {
                pendientes++;
                continue;
            }

            int pts = p.getPuntosObtenidos() != null ? p.getPuntosObtenidos() : 0;
            puntos += pts;

            if (pts == 3) {
                plenos++;
            } else if (pts == 1) {
                aciertos++;
            } else {
                incorrectos++;
            }
        }

        int finalizados = plenos + aciertos + incorrectos;
        double efectividad = finalizados > 0
                ? Math.round(((double)(plenos + aciertos) / finalizados) * 1000.0) / 10.0
                : 0.0;

        return EstadisticasResponseDto.builder()
                .usuarioId(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .totalPronosticos(total)
                .plenos(plenos)
                .aciertos(aciertos)
                .incorrectos(incorrectos)
                .pendientes(pendientes)
                .puntosTotales(puntos)
                .efectividad(efectividad)
                .build();
    }
}