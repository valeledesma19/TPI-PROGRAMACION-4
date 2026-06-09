package com.prode.tpi.feature.pronostico.service;

import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.partido.model.Partido;
import com.prode.tpi.feature.partido.repositories.PartidoRepository;
import com.prode.tpi.feature.pronostico.dto.PronosticoGroupedResponseDto;
import com.prode.tpi.feature.pronostico.mapper.PronosticoMapper;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import com.prode.tpi.feature.usuario.model.Usuario;
import com.prode.tpi.feature.usuario.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PronosticoServiceImpl implements PronosticoService {

    private final PronosticoRepository pronosticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PartidoRepository partidoRepository;

    @Override
    @Transactional
    public Pronostico crearOActualizarPronostico(
            Long usuarioId,
            Long partidoId,
            Integer golesLocal,
            Integer golesVisitante
    ) {

        // 1. Buscar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // 2. Buscar partido
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado"));

        // 3. Validar estado del partido
        if (partido.getEstado() != EstadoPartido.POR_JUGARSE) {
            throw new IllegalStateException("No se puede pronosticar un partido que ya comenzó o finalizó");
        }

        // 4. Validar regla de 30 minutos
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = partido.getFechaHoraInicio().minusMinutes(30);

        if (ahora.isAfter(limite)) {
            throw new IllegalStateException("Ya no se pueden realizar pronósticos (bloqueo de 30 minutos فعال)");
        }

        // 5. Buscar si ya existe pronóstico
        Optional<Pronostico> existenteOpt =
                pronosticoRepository.findByUsuarioAndPartido(usuario, partido);

        Pronostico pronostico;

        if (existenteOpt.isPresent()) {
            //  ACTUALIZAR
            pronostico = existenteOpt.get();
            pronostico.setGolesLocalPredicho(golesLocal);
            pronostico.setGolesVisitantePredicho(golesVisitante);
        } else {
            //  CREAR NUEVO
            pronostico = Pronostico.builder()
                    .usuario(usuario)
                    .partido(partido)
                    .golesLocalPredicho(golesLocal)
                    .golesVisitantePredicho(golesVisitante)
                    .fechaCreacion(ahora)
                    .puntosObtenidos(0)
                    .build();
        }

        return pronosticoRepository.save(pronostico);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Pronostico> verPronosticosDeTerceros(Long partidoId) {

        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado"));

        //  REGLA RF5.3: bloqueo de 30 minutos
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = partido.getFechaHoraInicio().minusMinutes(30);

        if (ahora.isBefore(limite)) {
            throw new IllegalStateException(
                    "Aún no podés ver pronósticos de otros usuarios (bloqueo activo)");
        }

        return pronosticoRepository.findByPartido(partido);
    }
    @Override
    @Transactional(readOnly = true)
    public PronosticoGroupedResponseDto obtenerPronosticosAgrupados(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Pronostico> pronosticos = pronosticoRepository.findByUsuario(usuario);

        return PronosticoGroupedResponseDto.builder()
                .porJugarse(
                        pronosticos.stream()
                                .filter(p -> p.getPartido().getEstado() == EstadoPartido.POR_JUGARSE)
                                .map(PronosticoMapper::toDto)
                                .toList()
                )
                .enJuego(
                        pronosticos.stream()
                                .filter(p -> p.getPartido().getEstado() == EstadoPartido.EN_JUEGO)
                                .map(PronosticoMapper::toDto)
                                .toList()
                )
                .finalizado(
                        pronosticos.stream()
                                .filter(p -> p.getPartido().getEstado() == EstadoPartido.FINALIZADO)
                                .map(PronosticoMapper::toDto)
                                .toList()
                )
                .build();
    }
}