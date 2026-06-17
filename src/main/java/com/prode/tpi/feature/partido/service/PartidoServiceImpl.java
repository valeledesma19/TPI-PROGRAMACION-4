package com.prode.tpi.feature.partido.service;

import com.prode.tpi.feature.equipo.model.Equipo;
import com.prode.tpi.feature.equipo.repositories.EquipoRepository;
import com.prode.tpi.feature.fecha.model.EstadoFecha;
import com.prode.tpi.feature.fecha.model.Fecha;
import com.prode.tpi.feature.fecha.repositories.FechaRepository;
import com.prode.tpi.feature.partido.dto.PartidoRequestDto;
import com.prode.tpi.feature.partido.dto.PartidoResponseDto;
import com.prode.tpi.feature.partido.dto.PartidoUpdateRequestDto;
import com.prode.tpi.feature.partido.model.EstadoPartido;
import com.prode.tpi.feature.partido.model.Partido;
import com.prode.tpi.feature.partido.repositories.PartidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prode.tpi.feature.partido.dto.ResultadoPartidoRequestDto;
import com.prode.tpi.feature.partido.model.TendenciaResultado;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import java.util.List;
import java.util.stream.Collectors;
import com.prode.tpi.feature.pronostico.model.Pronostico;
import com.prode.tpi.feature.pronostico.repositories.PronosticoRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;
    private final FechaRepository fechaRepository;
    private final PronosticoRepository pronosticoRepository;



    // ── RF4.1 Registro ────────────────────────────────────────────────────────

    @Override
    @Transactional
    public PartidoResponseDto crearPartido(PartidoRequestDto request) {
        validarEquiposDistintos(request.getIdEquipoLocal(), request.getIdEquipoVisitante());

        Fecha fecha = fechaRepository.findById(request.getIdFecha())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fecha no encontrada con id: " + request.getIdFecha()));

        Equipo local = equipoRepository.findById(request.getIdEquipoLocal())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Equipo local no encontrado con id: " + request.getIdEquipoLocal()));

        Equipo visitante = equipoRepository.findById(request.getIdEquipoVisitante())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Equipo visitante no encontrado con id: " + request.getIdEquipoVisitante()));

        Partido partido = Partido.builder()
                .fecha(fecha)
                .fechaHoraInicio(request.getFechaHoraInicio())
                .equipoLocal(local)
                .equipoVisitante(visitante)
                .estado(EstadoPartido.POR_JUGARSE)
                .build();

        return toResponseDto(partidoRepository.save(partido));
    }

    // ── RF4.2 Modificación ────────────────────────────────────────────────────

    @Override
    @Transactional
    public PartidoResponseDto modificarPartido(Long id, PartidoUpdateRequestDto request) {
        Partido partido = obtenerOLanzar(id);

        if (partido.getEstado() != EstadoPartido.POR_JUGARSE) {
            throw new IllegalStateException(
                    "Solo se puede modificar un partido en estado POR_JUGARSE."
            );
        }

        validarEquiposDistintos(request.getIdEquipoLocal(), request.getIdEquipoVisitante());

        Equipo local = equipoRepository.findById(request.getIdEquipoLocal())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Equipo local no encontrado con id: " + request.getIdEquipoLocal()));

        Equipo visitante = equipoRepository.findById(request.getIdEquipoVisitante())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Equipo visitante no encontrado con id: " + request.getIdEquipoVisitante()));

        List<Pronostico> pronosticosDelPartido = pronosticoRepository.findByPartido(partido);

        if (!pronosticosDelPartido.isEmpty()) {
            pronosticoRepository.deleteAll(pronosticosDelPartido);
        }

        partido.setFechaHoraInicio(request.getFechaHoraInicio());
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);

        return toResponseDto(partidoRepository.save(partido));
    }

    // ── RF4.3 Transición a EN_JUEGO ───────────────────────────────────────────

    @Override
    @Transactional
    public PartidoResponseDto pasarAEnJuego(Long id) {
        Partido partido = obtenerOLanzar(id);

        if (partido.getEstado() != EstadoPartido.POR_JUGARSE) {
            throw new IllegalStateException(
                    "Solo se puede pasar a EN_JUEGO un partido que esté POR_JUGARSE."
            );
        }

        LocalDateTime ahora = LocalDateTime.now();

        if (ahora.isBefore(partido.getFechaHoraInicio())) {
            throw new IllegalStateException(
                    "No se puede pasar el partido a EN_JUEGO antes de la fecha y hora programada."
            );
        }

        partido.setEstado(EstadoPartido.EN_JUEGO);
        Partido guardado = partidoRepository.save(partido);

        actualizarEstadoFecha(partido.getFecha());

        return toResponseDto(guardado);
    }
    @Override
    @Transactional
    public PartidoResponseDto cargarResultado(Long id, ResultadoPartidoRequestDto request) {
        Partido partido = obtenerOLanzar(id);

        if (partido.getEstado() != EstadoPartido.EN_JUEGO) {
            throw new IllegalStateException(
                    "Solo se puede cargar resultado a un partido en estado EN_JUEGO. Si ya está FINALIZADO, no se puede corregir."
            );
        }

        partido.setGolesLocal(request.getGolesLocal());
        partido.setGolesVisitante(request.getGolesVisitante());

        TendenciaResultado tendenciaReal = calcularTendencia(
                request.getGolesLocal(),
                request.getGolesVisitante()
        );

        partido.setTendenciaResultado(tendenciaReal);
        partido.setEstado(EstadoPartido.FINALIZADO);

        Partido partidoGuardado = partidoRepository.save(partido);

        List<Pronostico> pronosticos = pronosticoRepository.findByPartido(partidoGuardado);

        for (Pronostico pronostico : pronosticos) {
            int puntos = calcularPuntos(pronostico, partidoGuardado);
            pronostico.setPuntosObtenidos(puntos);
        }

        pronosticoRepository.saveAll(pronosticos);

        actualizarEstadoFecha(partidoGuardado.getFecha());

        return toResponseDto(partidoGuardado);
    }

    // ── RF4.4 Consulta ────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<PartidoResponseDto> listarPartidos(Long idFecha) {
        List<Partido> partidos = (idFecha != null)
                ? partidoRepository.findByFecha_IdFechaOrderByFechaHoraInicioAsc(idFecha)
                : partidoRepository.findAll();
        return partidos.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PartidoResponseDto obtenerPartidoPorId(Long id) {
        return toResponseDto(obtenerOLanzar(id));
    }

    // ── RF4.5 Eliminación ─────────────────────────────────────────────────────

    @Override
    @Transactional
    public void eliminarPartido(Long id) {
        Partido partido = obtenerOLanzar(id);

        if (partido.getEstado() != EstadoPartido.POR_JUGARSE) {
            throw new IllegalStateException(
                    "Solo se puede eliminar un partido en estado POR_JUGARSE.");
        }

        if (partido.getPronosticos() != null && !partido.getPronosticos().isEmpty()) {
            throw new IllegalStateException(
                    "No se puede eliminar el partido porque tiene pronósticos registrados.");
        }

        partidoRepository.delete(partido);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Partido obtenerOLanzar(Long id) {
        return partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Partido no encontrado con id: " + id));
    }

    private void validarEquiposDistintos(Long idLocal, Long idVisitante) {
        if (idLocal.equals(idVisitante)) {
            throw new IllegalArgumentException(
                    "El equipo local y el visitante no pueden ser el mismo.");
        }
    }

    private TendenciaResultado calcularTendencia(Integer golesLocal, Integer golesVisitante) {
        if (golesLocal > golesVisitante) {
            return TendenciaResultado.LOCAL;
        }

        if (golesLocal < golesVisitante) {
            return TendenciaResultado.VISITANTE;
        }

        return TendenciaResultado.EMPATE;
    }

    private int calcularPuntos(Pronostico pronostico, Partido partido) {

        boolean exacto =
                pronostico.getGolesLocalPredicho().equals(partido.getGolesLocal())
                        && pronostico.getGolesVisitantePredicho().equals(partido.getGolesVisitante());

        if (exacto) {
            return 3;
        }

        TendenciaResultado tendenciaPronostico = calcularTendencia(
                pronostico.getGolesLocalPredicho(),
                pronostico.getGolesVisitantePredicho()
        );

        if (tendenciaPronostico == partido.getTendenciaResultado()) {
            return 1;
        }

        return 0;
    }

    private void actualizarEstadoFecha(Fecha fecha) {

        List<Partido> partidos = partidoRepository
                .findByFecha_IdFechaOrderByFechaHoraInicioAsc(fecha.getIdFecha());

        boolean todosFinalizados = partidos.stream()
                .allMatch(p -> p.getEstado() == EstadoPartido.FINALIZADO);

        boolean algunoEnJuego = partidos.stream()
                .anyMatch(p -> p.getEstado() == EstadoPartido.EN_JUEGO);

        if (!partidos.isEmpty() && todosFinalizados) {
            fecha.setEstado(EstadoFecha.FINALIZADA);
        } else if (algunoEnJuego) {
            fecha.setEstado(EstadoFecha.EN_JUEGO);
        } else {
            fecha.setEstado(EstadoFecha.PROGRAMADA);
        }

        fechaRepository.save(fecha);
    }

    // ── Mapper ────────────────────────────────────────────────────────────────

    private PartidoResponseDto toResponseDto(Partido partido) {
        return PartidoResponseDto.builder()
                .idPartido(partido.getIdPartido())
                .fechaHoraInicio(partido.getFechaHoraInicio())
                .estado(partido.getEstado())
                .golesLocal(partido.getGolesLocal())
                .golesVisitante(partido.getGolesVisitante())
                .tendenciaResultado(partido.getTendenciaResultado())
                .idFecha(partido.getFecha().getIdFecha())
                .nombreFecha(partido.getFecha().getNombre())
                .idEquipoLocal(partido.getEquipoLocal().getIdEquipo())
                .nombreEquipoLocal(partido.getEquipoLocal().getNombre())
                .idEquipoVisitante(partido.getEquipoVisitante().getIdEquipo())
                .nombreEquipoVisitante(partido.getEquipoVisitante().getNombre())
                .build();
    }

}
