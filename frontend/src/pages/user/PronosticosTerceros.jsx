import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PronosticosTerceros.css";

function PronosticosTerceros() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const [partidoId, setPartidoId] = useState("");
  const [pronosticos, setPronosticos] = useState([]);
  const [mensaje, setMensaje] = useState("");

  const consultarPronosticos = async () => {
    if (!partidoId.trim()) {
      setMensaje("Ingresá el ID del partido.");
      return;
    }

    const res = await fetch(
      `http://localhost:8080/api/pronosticos/partido/${partidoId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (!res.ok) {
      setPronosticos([]);
      setMensaje("No se pueden ver todavía o el partido no existe.");
      return;
    }

    const data = await res.json();
    setPronosticos(data);
    setMensaje("");
  };

  return (
    <div className="dashboard-bg">
      <aside className="sidebar">
        <h1 className="logo">PR⚽DE</h1>

        <button className="side-item" onClick={() => navigate("/user")}>
          Dashboard
        </button>

        <button className="side-item" onClick={() => navigate("/user/pronosticos")}>
          Mis Pronósticos
        </button>

        <button className="side-item active">
          Pronósticos de Terceros
        </button>

        <button className="side-item">Ranking</button>
        <button className="side-item">Partidos</button>
      </aside>

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

        <section className="terceros-grid">
          <div className="dash-card big">
            <h3>Buscar por partido</h3>
            <p>Ingresá el ID del partido para consultar los pronósticos disponibles.</p>

            <div className="terceros-form">
              <input
                type="number"
                placeholder="ID del partido"
                value={partidoId}
                onChange={(e) => setPartidoId(e.target.value)}
              />

              <button onClick={consultarPronosticos}>
                Consultar
              </button>
            </div>

            {mensaje && <p className="terceros-error">{mensaje}</p>}
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