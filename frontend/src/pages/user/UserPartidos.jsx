import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import "../Dashboard.css";
import "../AdminCrud.css";

function UserPartidos() {
  const [partidos, setPartidos] = useState([]);
  const [fechas, setFechas] = useState([]);
  const [filtroFecha, setFiltroFecha] = useState("");
  const [mensaje, setMensaje] = useState("");

  const cargarDatos = async () => {
    try {
      const query = filtroFecha ? `?idFecha=${filtroFecha}` : "";

      const [partidosData, fechasData] = await Promise.all([
        apiFetch(`/partidos${query}`),
        apiFetch("/Fecha/Listar"),
      ]);

      setPartidos(partidosData);
      setFechas(fechasData);
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarDatos();
  }, [filtroFecha]);

  return (
    <div className="dashboard-bg">
      <Sidebar type="USER" active="partidos" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Partidos</h2>
            <p>Consultá los partidos del sistema filtrando por fecha.</p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="dash-card crud-card">
          <h3>Filtrar por fecha</h3>

          <div className="crud-form">
            <select
              value={filtroFecha}
              onChange={(e) => setFiltroFecha(e.target.value)}
            >
              <option value="">Todas las fechas</option>

              {fechas.map((fecha) => (
                <option key={fecha.idFecha} value={fecha.idFecha}>
                  {fecha.nombre}
                </option>
              ))}
            </select>
          </div>
        </section>

        <section className="dash-card crud-list-card">
          <h3>Listado de partidos</h3>

          <div className="crud-list">
            {partidos.length === 0 ? (
              <p className="empty">No hay partidos para mostrar.</p>
            ) : (
              partidos.map((p) => (
                <div className="crud-item" key={p.idPartido}>
                  <div>
                    <strong>
                      {p.nombreEquipoLocal} vs {p.nombreEquipoVisitante}
                    </strong>

                    <p>
                      Fecha: {p.nombreFecha} | Estado: {p.estado}
                    </p>

                    <p>
                      Inicio: {p.fechaHoraInicio?.replace("T", " ")}
                    </p>

                    {p.estado === "FINALIZADO" && (
                      <p>
                        Resultado final: {p.golesLocal} - {p.golesVisitante} | Tendencia: {p.tendenciaResultado}
                      </p>
                    )}
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

export default UserPartidos;