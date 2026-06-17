import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import "../Dashboard.css";
import "../AdminCrud.css";
import "./PronosticosTerceros.css";

function PronosticosTerceros() {
  const [partidos, setPartidos] = useState([]);
  const [partidoId, setPartidoId] = useState("");
  const [pronosticos, setPronosticos] = useState([]);
  const [mensaje, setMensaje] = useState("");

  const cargarPartidos = async () => {
    try {
      const data = await apiFetch("/partidos");
      setPartidos(data);
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarPartidos();
  }, []);

  const consultarPronosticos = async () => {
    if (!partidoId) {
      setMensaje("Seleccioná un partido.");
      return;
    }

    try {
      const data = await apiFetch(`/pronosticos/partido/${partidoId}`);
      setPronosticos(data);
      setMensaje("");
    } catch (error) {
      setPronosticos([]);
      setMensaje(error.message);
    }
  };

  return (
    <div className="dashboard-bg">
      <Sidebar type="USER" active="terceros" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Pronósticos de Terceros</h2>
            <p>
              Consultá qué predijeron otros usuarios para un partido.
              Solo se habilita cuando vence el bloqueo de 30 minutos.
            </p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="dash-card crud-card">
          <h3>Buscar por partido</h3>

          <div className="crud-form">
            <select
              value={partidoId}
              onChange={(e) => setPartidoId(e.target.value)}
            >
              <option value="">Seleccionar partido</option>

              {partidos.map((p) => (
                <option key={p.idPartido} value={p.idPartido}>
                  {p.nombreEquipoLocal} vs {p.nombreEquipoVisitante} | {p.nombreFecha} | {p.estado}
                </option>
              ))}
            </select>

            <button onClick={consultarPronosticos}>
              Consultar pronósticos
            </button>
          </div>
        </section>

        <section className="dash-card resultados-card">
          <h3>Resultados</h3>

          <div className="terceros-list">
            {pronosticos.length === 0 ? (
              <p className="empty">No hay pronósticos para mostrar.</p>
            ) : (
              pronosticos.map((p) => (
                <div className="tercero-item" key={p.idPronostico}>
                  <div>
                    <strong>Usuario #{p.usuarioId}</strong>
                    <p>Partido #{p.partidoId}</p>
                  </div>

                  <div className="resultado-tercero">
                    {p.golesLocalPredicho} - {p.golesVisitantePredicho}
                  </div>

                  <span className="puntos-tercero">
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

export default PronosticosTerceros;