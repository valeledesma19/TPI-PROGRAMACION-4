import { useNavigate } from "react-router-dom";
import "./Dashboard.css";

function User() {
  const navigate = useNavigate();

  return (
    <div className="dashboard-bg">
      <aside className="sidebar">
        <h1 className="logo">PR⚽DE</h1>

        <button className="side-item active">Dashboard</button>

        <button className="side-item" onClick={() => navigate("/user/pronosticos")}>
          Mis Pronósticos
        </button>

        <button className="side-item" onClick={() => navigate("/user/pronosticos-terceros")}>
          Pronósticos de Terceros
        </button>

        <button className="side-item">Ranking</button>
        <button className="side-item">Partidos</button>
      </aside>

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Panel de Usuario</h2>
            <p>Gestioná tus pronósticos y consultá predicciones de otros usuarios.</p>
          </div>

          <div className="admin-user">Usuario</div>
        </header>

        <section className="dashboard-grid">
          <div className="dash-card big">
            <h3>Bienvenido al PRODE</h3>
            <p>Realizá predicciones, sumá puntos y competí en el ranking.</p>

            <div className="stats">
              <div>
                <strong>15</strong>
                <span>Pronósticos</span>
              </div>

              <div>
                <strong>42</strong>
                <span>Puntos</span>
              </div>

              <div>
                <strong>7°</strong>
                <span>Ranking</span>
              </div>
            </div>
          </div>

          <div className="dash-card" onClick={() => navigate("/user/pronosticos")}>
            <h3>Mis Pronósticos</h3>
            <p>Crear, modificar y consultar tus predicciones.</p>
            <button>Ir a Mis Pronósticos</button>
          </div>

          <div className="dash-card" onClick={() => navigate("/user/pronosticos-terceros")}>
            <h3>Pronósticos de Terceros</h3>
            <p>Consultá qué predijeron otros usuarios para un partido.</p>
            <button>Ver terceros</button>
          </div>
        </section>
      </main>
    </div>
  );
}

export default User;