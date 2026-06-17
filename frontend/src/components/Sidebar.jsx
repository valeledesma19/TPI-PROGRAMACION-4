import { useNavigate } from "react-router-dom";
import { logout } from "../utils/auth";

function Sidebar({ type, active }) {
  const navigate = useNavigate();

  return (
    <aside className="sidebar">
      <h1 className="logo">PR⚽DE</h1>

      {type === "ADMIN" && (
        <>
          <button
            className={`side-item ${active === "dashboard" ? "active" : ""}`}
            onClick={() => navigate("/admin")}
          >
            Dashboard
          </button>

          <button
            className={`side-item ${active === "equipos" ? "active" : ""}`}
            onClick={() => navigate("/admin/equipos")}
          >
            Equipos
          </button>

          <button
            className={`side-item ${active === "fechas" ? "active" : ""}`}
            onClick={() => navigate("/admin/fechas")}
          >
            Fechas
          </button>

          <button
            className={`side-item ${active === "partidos" ? "active" : ""}`}
            onClick={() => navigate("/admin/partidos")}
          >
            Partidos
          </button>

          <button
            className={`side-item ${active === "ranking" ? "active" : ""}`}
            onClick={() => navigate("/ranking")}
          >
            Ranking
          </button>
        </>
      )}

      {type === "USER" && (
        <>
          <button
            className={`side-item ${active === "dashboard" ? "active" : ""}`}
            onClick={() => navigate("/user")}
          >
            Dashboard
          </button>

          <button
            className={`side-item ${active === "fechas" ? "active" : ""}`}
            onClick={() => navigate("/user/fechas")}
          >
            Fechas
          </button>

          <button
            className={`side-item ${active === "partidos" ? "active" : ""}`}
            onClick={() => navigate("/user/partidos")}
          >
            Partidos
          </button>

          <button
            className={`side-item ${active === "pronosticos" ? "active" : ""}`}
            onClick={() => navigate("/user/pronosticos")}
          >
            Mis Pronósticos
          </button>

          <button
            className={`side-item ${active === "terceros" ? "active" : ""}`}
            onClick={() => navigate("/user/pronosticos-terceros")}
          >
            Pronósticos de Terceros
          </button>

          <button
            className={`side-item ${active === "ranking" ? "active" : ""}`}
            onClick={() => navigate("/ranking")}
          >
            Ranking
          </button>
        </>
      )}

      <button
        className="side-item"
        style={{ marginTop: "auto" }}
        onClick={() => logout(navigate)}
      >
        Cerrar sesión
      </button>
    </aside>
  );
}

export default Sidebar;