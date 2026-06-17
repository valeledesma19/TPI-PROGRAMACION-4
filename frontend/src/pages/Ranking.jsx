import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import { apiFetch } from "../utils/api";
import "./Dashboard.css";
import "./AdminCrud.css";

function Ranking() {
  const [ranking, setRanking] = useState([]);
  const [mensaje, setMensaje] = useState("");

  const rol = localStorage.getItem("rol");

  const cargarRanking = async () => {
    try {
      const data = await apiFetch("/ranking/global");
      setRanking(data);
      setMensaje("");
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarRanking();
  }, []);

  const formatearFecha = (fecha) => {
    if (!fecha) return "Sin pronósticos";

    return new Date(fecha).toLocaleString("es-AR", {
      dateStyle: "short",
      timeStyle: "short",
    });
  };

  return (
    <div className="dashboard-bg">
      <Sidebar type={rol === "ADMIN" ? "ADMIN" : "USER"} active="ranking" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Ranking Global</h2>
            <p>Tabla de posiciones general ordenada por puntos acumulados.</p>
          </div>

          <div className="admin-user">
            {rol === "ADMIN" ? "Administrador" : "Usuario"}
          </div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="dash-card crud-list-card">
          <h3>Tabla de posiciones</h3>

          <div className="crud-list">
            {ranking.length === 0 ? (
              <p className="empty">No hay usuarios para mostrar.</p>
            ) : (
              ranking.map((usuario) => (
                <div className="crud-item" key={usuario.usuarioId}>
                  <div>
                    <strong>
                      #{usuario.posicion} - {usuario.nombre}
                    </strong>

                    <p>{usuario.email}</p>

                    <p>
                      Plenos acertados: {usuario.plenos} | Primer pronóstico:{" "}
                      {formatearFecha(usuario.fechaPrimerPronostico)}
                    </p>
                  </div>

                  <div className="crud-actions">
                    <span className="estado-pronostico">
                      {usuario.puntosTotales} pts
                    </span>
                  </div>
                </div>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default Ranking;