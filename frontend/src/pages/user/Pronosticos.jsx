import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import { getUserFromToken } from "../../utils/auth";
import "../Dashboard.css";
import "../AdminCrud.css";
import "./Pronosticos.css";

function Pronosticos() {
  const user = getUserFromToken();
  const usuarioId = user?.idUsuario || localStorage.getItem("usuarioId");

  const [partidos, setPartidos] = useState([]);
  const [pronosticosAgrupados, setPronosticosAgrupados] = useState({
    porJugarse: [],
    enJuego: [],
    finalizado: [],
  });

  const [filtroEstado, setFiltroEstado] = useState("TODOS");
  const [partidoSeleccionado, setPartidoSeleccionado] = useState("");
  const [golesLocal, setGolesLocal] = useState("");
  const [golesVisitante, setGolesVisitante] = useState("");
  const [mensaje, setMensaje] = useState("");

  const cargarPartidos = async () => {
    try {
      const data = await apiFetch("/partidos");
      setPartidos(data);
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const cargarMisPronosticos = async () => {
    if (!usuarioId) {
      setMensaje("No se encontró el usuario logueado.");
      return;
    }

    try {
      const data = await apiFetch(`/pronosticos/usuario/${usuarioId}/agrupados`);

      setPronosticosAgrupados({
        porJugarse: data.porJugarse || [],
        enJuego: data.enJuego || [],
        finalizado: data.finalizado || [],
      });
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarPartidos();
    cargarMisPronosticos();
  }, []);

  const guardarPronostico = async () => {
    if (!usuarioId) {
      setMensaje("No se encontró el usuario logueado.");
      return;
    }

    if (!partidoSeleccionado) {
      setMensaje("Seleccioná un partido.");
      return;
    }

    if (golesLocal === "" || golesVisitante === "") {
      setMensaje("Completá los goles de ambos equipos.");
      return;
    }

    try {
      await apiFetch(`/pronosticos/${usuarioId}`, {
        method: "POST",
        body: JSON.stringify({
          partidoId: Number(partidoSeleccionado),
          golesLocalPredicho: Number(golesLocal),
          golesVisitantePredicho: Number(golesVisitante),
        }),
      });

      setPartidoSeleccionado("");
      setGolesLocal("");
      setGolesVisitante("");
      setMensaje("Pronóstico guardado correctamente.");

      cargarMisPronosticos();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const pronosticosFiltrados = () => {
    if (filtroEstado === "POR_JUGARSE") return pronosticosAgrupados.porJugarse;
    if (filtroEstado === "EN_JUEGO") return pronosticosAgrupados.enJuego;
    if (filtroEstado === "FINALIZADO") return pronosticosAgrupados.finalizado;

    return [
      ...pronosticosAgrupados.porJugarse,
      ...pronosticosAgrupados.enJuego,
      ...pronosticosAgrupados.finalizado,
    ];
  };

  const partidosDisponibles = partidos.filter(
    (p) => p.estado === "POR_JUGARSE"
  );

  const obtenerNombrePartido = (partidoId) => {
    const partido = partidos.find((p) => p.idPartido === partidoId);

    if (!partido) return `Partido #${partidoId}`;

    return `${partido.nombreEquipoLocal} vs ${partido.nombreEquipoVisitante}`;
  };

  return (
    <div className="dashboard-bg">
      <Sidebar type="USER" active="pronosticos" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Mis Pronósticos</h2>
            <p>Creá, modificá y consultá tus predicciones.</p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="pronosticos-top">
          <div className="dash-card pronostico-crear">
            <h3>Crear o modificar pronóstico</h3>
            <p>Seleccioná un partido por jugarse y cargá tu predicción.</p>

            <div className="pronostico-form">
              <select
                value={partidoSeleccionado}
                onChange={(e) => setPartidoSeleccionado(e.target.value)}
              >
                <option value="">Seleccionar partido</option>

                {partidosDisponibles.map((p) => (
                  <option key={p.idPartido} value={p.idPartido}>
                    {p.nombreEquipoLocal} vs {p.nombreEquipoVisitante} | {p.nombreFecha} | {p.fechaHoraInicio?.replace("T", " ")}
                  </option>
                ))}
              </select>

              <div className="goles-row">
                <input
                  type="number"
                  min="0"
                  placeholder="Goles local"
                  value={golesLocal}
                  onChange={(e) => setGolesLocal(e.target.value)}
                />

                <input
                  type="number"
                  min="0"
                  placeholder="Goles visitante"
                  value={golesVisitante}
                  onChange={(e) => setGolesVisitante(e.target.value)}
                />
              </div>

              <button onClick={guardarPronostico}>
                Guardar pronóstico
              </button>
            </div>
          </div>

          <div className="dash-card filtros-card">
            <h3>Filtrar por estado</h3>
            <p>Consultá tus pronósticos según el estado del partido.</p>

            <div className="filtros-pronosticos">
              <button
                className={filtroEstado === "TODOS" ? "active-filter" : ""}
                onClick={() => setFiltroEstado("TODOS")}
              >
                Todos
              </button>

              <button
                className={filtroEstado === "POR_JUGARSE" ? "active-filter" : ""}
                onClick={() => setFiltroEstado("POR_JUGARSE")}
              >
                Por jugarse
              </button>

              <button
                className={filtroEstado === "EN_JUEGO" ? "active-filter" : ""}
                onClick={() => setFiltroEstado("EN_JUEGO")}
              >
                En juego
              </button>

              <button
                className={filtroEstado === "FINALIZADO" ? "active-filter" : ""}
                onClick={() => setFiltroEstado("FINALIZADO")}
              >
                Finalizados
              </button>
            </div>
          </div>
        </section>

        <section className="dash-card resultados-pronosticos">
          <div className="section-title">
            <div>
              <h3>Mis predicciones</h3>
              <p>Listado de pronósticos realizados.</p>
            </div>

            <span>{pronosticosFiltrados().length} registros</span>
          </div>

          <div className="pronosticos-lista">
            {pronosticosFiltrados().length === 0 ? (
              <p className="empty">No hay pronósticos para mostrar.</p>
            ) : (
              pronosticosFiltrados().map((p) => (
                <div className="pronostico-card" key={p.idPronostico}>
                  <div>
                    <strong>{obtenerNombrePartido(p.partidoId)}</strong>
                    <p>Pronóstico propio</p>
                  </div>

                  <div className="resultado-pronostico">
                    {p.golesLocalPredicho} - {p.golesVisitantePredicho}
                  </div>

                  <span className="estado-pronostico">
                    {p.puntosObtenidos} pts
                  </span>
                </div>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default Pronosticos;