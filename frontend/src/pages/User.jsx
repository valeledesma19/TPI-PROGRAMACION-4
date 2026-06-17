import { useNavigate } from "react-router-dom";
import Sidebar from "../components/Sidebar";
import "./Dashboard.css";

function User() {
  const navigate = useNavigate();

  return (
    <div className="dashboard-bg">
      <Sidebar type="USER" active="dashboard" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Panel de Usuario</h2>
            <p>Consultá partidos, cargá pronósticos y mirá predicciones de terceros.</p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        <section className="dashboard-grid">
          <div className="dash-card big">
            <h3>Bienvenido al PRODE</h3>
            <p>Realizá predicciones, sumá puntos y competí en el ranking.</p>

            <div className="stats">
              <div>
                <strong>RF4</strong>
                <span>Partidos</span>
              </div>

              <div>
                <strong>RF5.1</strong>
                <span>Pronósticos</span>
              </div>

              <div>
                <strong>RF5.3</strong>
                <span>Terceros</span>
              </div>
            </div>
          </div>

          <div className="dash-card" onClick={() => navigate("/user/partidos")}>
            <h3>Partidos</h3>
            <p>Listá los partidos disponibles del sistema.</p>
            <button>Ver partidos</button>
          </div>

          <div className="dash-card" onClick={() => navigate("/user/pronosticos")}>
            <h3>Mis Pronósticos</h3>
            <p>Crear, modificar y consultar tus predicciones.</p>
            <button>Ir a pronósticos</button>
          </div>

          <div className="dash-card" onClick={() => navigate("/user/pronosticos-terceros")}>
            <h3>Pronósticos de Terceros</h3>
            <p>Consultá qué predijeron otros usuarios.</p>
            <button>Ver terceros</button>
          </div>
        </section>
      </main>
    </div>
  );
}

export default User;