import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import "../Dashboard.css";
import "./MisEstadisticas.css";

function getUsuarioIdFromStorage() {
  const id = localStorage.getItem("usuarioId");
  return id ? Number(id) : null;
}

function MisEstadisticas() {
  const [stats, setStats]       = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError]       = useState("");

  const usuarioId = getUsuarioIdFromStorage();

  useEffect(() => {
    if (!usuarioId) {
      setError("No se pudo identificar al usuario.");
      setCargando(false);
      return;
    }

    apiFetch(`/estadisticas/usuario/${usuarioId}`)
      .then((data) => setStats(data))
      .catch((e) => setError(e.message))
      .finally(() => setCargando(false));
  }, [usuarioId]);

  return (
    <div className="dashboard-bg">
      <Sidebar type="USER" active="estadisticas" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Mis Estadísticas</h2>
            <p>Resumen de tu rendimiento en el PRODE.</p>
          </div>
          <div className="admin-user">Usuario</div>
        </header>

        {error && <div className="crud-message">{error}</div>}

        {cargando ? (
          <div className="stats-loading">Cargando estadísticas…</div>
        ) : !stats ? null : stats.totalPronosticos === 0 ? (
          <div className="dash-card">
            <div className="stats-empty">
              <span className="big-icon">📊</span>
              <p>Todavía no realizaste ningún pronóstico.</p>
              <p>¡Empezá a predecir para ver tus estadísticas aquí!</p>
            </div>
          </div>
        ) : (
          <>
            <div className="stats-grid">
              <div className="stat-card">
                <span className="stat-icon">🎯</span>
                <span className="stat-value">{stats.totalPronosticos}</span>
                <span className="stat-label">Pronósticos realizados</span>
              </div>

              <div className="stat-card">
                <span className="stat-icon">🏆</span>
                <span className="stat-value green">{stats.plenos}</span>
                <span className="stat-label">Plenos (resultado exacto)</span>
              </div>

              <div className="stat-card">
                <span className="stat-icon">✅</span>
                <span className="stat-value yellow">{stats.aciertos}</span>
                <span className="stat-label">Aciertos de tendencia</span>
              </div>

              <div className="stat-card">
                <span className="stat-icon">❌</span>
                <span className="stat-value red">{stats.incorrectos}</span>
                <span className="stat-label">Pronósticos incorrectos</span>
              </div>

              <div className="stat-card">
                <span className="stat-icon">⏳</span>
                <span className="stat-value gray">{stats.pendientes}</span>
                <span className="stat-label">Pendientes de resolución</span>
              </div>

              <div className="stat-card">
                <span className="stat-icon">⭐</span>
                <span className="stat-value purple">{stats.puntosTotales}</span>
                <span className="stat-label">Puntos totales acumulados</span>
              </div>
            </div>

            <div className="dash-card efectividad-card">
              <div className="efectividad-header">
                <h3>Efectividad sobre partidos finalizados</h3>
                <span className="efectividad-pct">{stats.efectividad}%</span>
              </div>

              <div className="bar-track">
                <div
                  className="bar-fill"
                  style={{ width: `${stats.efectividad}%` }}
                />
              </div>

              <div className="efectividad-legend">
                <span style={{ color: "#4ade80" }}>
                  {stats.plenos} plenos (3 pts)
                </span>
                <span style={{ color: "#facc15" }}>
                  {stats.aciertos} tendencias (1 pt)
                </span>
                <span style={{ color: "#f87171" }}>
                  {stats.incorrectos} incorrectos (0 pts)
                </span>
              </div>
            </div>
          </>
        )}
      </main>
    </div>
  );
}

export default MisEstadisticas;