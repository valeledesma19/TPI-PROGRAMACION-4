import { useNavigate } from "react-router-dom";
import "./Dashboard.css";

function Admin() {
  const navigate = useNavigate();

  return (
    <div className="dashboard-bg">
      <aside className="sidebar">
        <h1 className="logo">PR⚽DE</h1>

        <button className="side-item active">Dashboard</button>
        <button className="side-item" onClick={() => navigate("/admin/equipos")}>
          Equipos
        </button>
        <button className="side-item">Partidos</button>
        <button className="side-item">Fechas</button>
        <button className="side-item">Ranking</button>
      </aside>

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Panel de Administración</h2>
            <p>Gestión del sistema PRODE</p>
          </div>

          <div className="admin-user">Administrador</div>
        </header>

        <section className="dashboard-grid">
          <div className="dash-card big">
            <h3>Resumen del sistema</h3>
            <p>Administrá equipos, partidos, fechas y resultados.</p>

            <div className="stats">
              <div>
                <strong>18</strong>
                <span>Equipos</span>
              </div>
              <div>
                <strong>980</strong>
                <span>Usuarios</span>
              </div>
              <div>
                <strong>12</strong>
                <span>Fechas</span>
              </div>
            </div>
          </div>

          <div className="dash-card" onClick={() => navigate("/admin/equipos")}>
            <h3>Equipos</h3>
            <p>Crear, listar, buscar y eliminar equipos.</p>
            <button>Gestionar equipos</button>
          </div>

          <div className="dash-card">
            <h3>Partidos</h3>
            <p>Administración de partidos del torneo.</p>
            <button>Próximamente</button>
          </div>
        </section>
      </main>
    </div>
  );
}

export default Admin;