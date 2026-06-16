import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Pronosticos.css";

function Pronosticos() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const usuarioId = localStorage.getItem("usuarioId");

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

  const API = "http://localhost:8080/api";

  const cargarPartidos = async () => {
    const res = await fetch(`${API}/partidos`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) return;

    const data = await res.json();
    setPartidos(data);
  };

  const cargarMisPronosticos = async () => {
    if (!usuarioId) {
      setMensaje("No se encontró el usuario logueado.");
      return;
    }

    const res = await fetch(`${API}/pronosticos/usuario/${usuarioId}/agrupados`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) {
      setMensaje("No se pudieron cargar tus pronósticos.");
      return;
    }

    const data = await res.json();

    setPronosticosAgrupados({
      porJugarse: data.porJugarse || [],
      enJuego: data.enJuego || [],
      finalizado: data.finalizado || [],
    });

    setMensaje("");
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

    const res = await fetch(`${API}/pronosticos/${usuarioId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        partidoId: Number(partidoSeleccionado),
        golesLocalPredicho: Number(golesLocal),
        golesVisitantePredicho: Number(golesVisitante),
      }),
    });

    if (!res.ok) {
      setMensaje(
        "No se pudo guardar. El partido debe estar por jugarse y faltar más de 30 minutos."
      );
      return;
    }

    setPartidoSeleccionado("");
    setGolesLocal("");
    setGolesVisitante("");
    setMensaje("Pronóstico guardado correctamente.");
    cargarMisPronosticos();
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

  return (
    <div className="dashboard-bg">
      <aside className="sidebar">
        <h1 className="logo">PR⚽DE</h1>

        <button className="side-item" onClick={() => navigate("/user")}>
          Dashboard
        </button>

        <button className="side-item active">Mis Pronósticos</button>

        <button
          className="side-item"
          onClick={() => navigate("/user/pronosticos-terceros")}
        >
          Pronósticos de Terceros
        </button>

        <button className="side-item">Ranking</button>
        <button className="side-item">Partidos</button>
      </aside>

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Mis Pronósticos</h2>
            <p>Creá, modificá y consultá tus predicciones.</p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        {mensaje && <div className="pronostico-message">{mensaje}</div>}

        <section className="pronosticos-top">
          <div className="dash-card pronostico-crear">
            <h3>Crear o modificar pronóstico</h3>
            <p>Seleccioná un partido y cargá el resultado que querés predecir.</p>

            <div className="pronostico-form">
              <select
                value={partidoSeleccionado}
                onChange={(e) => setPartidoSeleccionado(e.target.value)}
              >
                <option value="">Seleccionar partido</option>

                {partidos.map((p) => (
                  <option key={p.idPartido} value={p.idPartido}>
                    {p.equipoLocal?.nombre} vs {p.equipoVisitante?.nombre}
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

              <button onClick={guardarPronostico}>Guardar pronóstico</button>
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
                    <strong>Partido #{p.partidoId}</strong>
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