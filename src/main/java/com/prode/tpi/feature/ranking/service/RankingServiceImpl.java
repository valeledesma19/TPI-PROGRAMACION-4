package com.prode.tpi.feature.ranking.service;

import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import com.prode.tpi.feature.ranking.dto.RankingResponseDto;
import com.prode.tpi.feature.usuario.model.Rol;
import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final UsuarioRepository usuarioRepository;
    private final PronosticoRepository pronosticoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RankingResponseDto> rankingGlobal() {

        List<Usuario> usuarios = usuarioRepository.findByRol(Rol.USER);

        List<RankingResponseDto> ranking = usuarios.stream()
                .map(this::armarRankingUsuario)
                .collect(Collectors.toList());

        ranking.sort(
                Comparator
                        .comparing(
                                RankingResponseDto::getPuntosTotales,
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                RankingResponseDto::getPlenos,
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                RankingResponseDto::getFechaPrimerPronostico,
                                Comparator.nullsLast(Comparator.naturalOrder())
                        )
                        .thenComparing(
                                RankingResponseDto::getNombre,
                                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                        )
        );

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicion(i + 1);
        }

        return ranking;
    }

    private RankingResponseDto armarRankingUsuario(Usuario usuario) {

        List<Pronostico> pronosticos = pronosticoRepository.findByUsuario(usuario);

        int puntosTotales = pronosticos.stream()
                .mapToInt(p -> p.getPuntosObtenidos() != null ? p.getPuntosObtenidos() : 0)
                .sum();

        long plenos = pronosticos.stream()
                .filter(p -> p.getPuntosObtenidos() != null && p.getPuntosObtenidos() == 3)
                .count();

        LocalDateTime fechaPrimerPronostico = pronosticos.stream()
                .map(Pronostico::getFechaCreacion)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        return RankingResponseDto.builder()
                .posicion(0)
                .usuarioId(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .puntosTotales(puntosTotales)
                .plenos(plenos)
                .fechaPrimerPronostico(fechaPrimerPronostico)
                .build();
    }
}