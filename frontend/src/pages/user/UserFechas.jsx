import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import "../Dashboard.css";
import "../AdminCrud.css";

function UserFechas() {
  const [fechas, setFechas] = useState([]);
  const [estadoFiltro, setEstadoFiltro] = useState("");
  const [mensaje, setMensaje] = useState("");

  const cargarFechas = async () => {
    try {
      const query = estadoFiltro ? `?estado=${estadoFiltro}` : "";
      const data = await apiFetch(`/Fecha/Listar${query}`);
      setFechas(data);
      setMensaje("");
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarFechas();
  }, [estadoFiltro]);

  return (
    <div className="dashboard-bg">
      <Sidebar type="USER" active="fechas" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Fechas</h2>
            <p>Consultá las jornadas del sistema y filtrá por estado.</p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="dash-card crud-card">
          <h3>Filtrar fechas</h3>

          <div className="crud-form">
            <select
              value={estadoFiltro}
              onChange={(e) => setEstadoFiltro(e.target.value)}
            >
              <option value="">Todos los estados</option>
              <option value="PROGRAMADA">Programada</option>
              <option value="EN_JUEGO">En juego</option>
              <option value="FINALIZADA">Finalizada</option>
            </select>
          </div>
        </section>

        <section className="dash-card crud-list-card">
          <h3>Listado de fechas</h3>

          <div className="crud-list">
            {fechas.length === 0 ? (
              <p className="empty">No hay fechas para mostrar.</p>
            ) : (
              fechas.map((fecha) => (
                <div className="crud-item" key={fecha.idFecha}>
                  <div>
                    <strong>{fecha.nombre}</strong>
                    <p>Estado: {fecha.estado}</p>
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

export default UserFechas;