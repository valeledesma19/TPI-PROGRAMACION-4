import { useNavigate } from "react-router-dom";
import Sidebar from "../components/Sidebar";
import "./Dashboard.css";

function Admin() {
  const navigate = useNavigate();

  return (
    <div className="dashboard-bg">
      <Sidebar type="ADMIN" active="dashboard" />

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
            <p>Administrá equipos, fechas, partidos y resultados.</p>
          </div>

          <div className="dash-card" onClick={() => navigate("/admin/equipos")}>
            <h3>Equipos</h3>
            <p>Crear, listar, buscar y eliminar equipos.</p>
            <button>Gestionar equipos</button>
          </div>

          <div className="dash-card" onClick={() => navigate("/admin/fechas")}>
            <h3>Fechas</h3>
            <p>Crear, modificar, listar y eliminar fechas.</p>
            <button>Gestionar fechas</button>
          </div>

          <div className="dash-card" onClick={() => navigate("/admin/partidos")}>
            <h3>Partidos</h3>
            <p>Crear, modificar, eliminar y pasar partidos a en juego.</p>
            <button>Gestionar partidos</button>
          </div>
        </section>
      </main>
    </div>
  );
}

export default Admin;