import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { getUserFromToken } from "../../utils/auth";
import "../Dashboard.css";
import "./equipos.css";

function Equipos() {
  const [equipos, setEquipos] = useState([]);
  const [nombre, setNombre] = useState("");
  const [busqueda, setBusqueda] = useState("");
  const [mensaje, setMensaje] = useState("");

  const token = localStorage.getItem("token");
  const user = getUserFromToken();

  const API = "http://localhost:8080/api/equipos";

  const fetchEquipos = async (filtro = "") => {
    try {
      const url = filtro ? `${API}?nombre=${filtro}` : API;

      const res = await fetch(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!res.ok) {
        setMensaje("No se pudieron cargar los equipos.");
        return;
      }

      const data = await res.json();
      setEquipos(data);
      setMensaje("");
    } catch {
      setMensaje("Error de conexión con el servidor.");
    }
  };

  useEffect(() => {
    fetchEquipos();
  }, []);

  const crearEquipo = async () => {
    if (!nombre.trim()) {
      setMensaje("Ingresá el nombre del equipo.");
      return;
    }

    try {
      const res = await fetch(API, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ nombre }),
      });

      if (!res.ok) {
        setMensaje("No se pudo crear el equipo.");
        return;
      }

      setNombre("");
      setMensaje("Equipo creado correctamente.");
      fetchEquipos();
    } catch {
      setMensaje("Error de conexión con el servidor.");
    }
  };

  const eliminarEquipo = async (id) => {
    const confirmar = confirm("¿Seguro que querés eliminar este equipo?");
    if (!confirmar) return;

    try {
      const res = await fetch(`${API}/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!res.ok) {
        setMensaje("No se pudo eliminar. Puede estar asociado a un partido.");
        return;
      }

      setMensaje("Equipo eliminado correctamente.");
      fetchEquipos();
    } catch {
      setMensaje("Error de conexión con el servidor.");
    }
  };

  if (user?.rol !== "ADMIN") {
    return <h2>No tenés permisos</h2>;
  }

  return (
    <div className="dashboard-bg">
      <Sidebar type="ADMIN" active="equipos" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Gestión de Equipos</h2>
            <p>Crear, listar, buscar y eliminar equipos del sistema.</p>
          </div>

          <div className="admin-user">Administrador</div>
        </header>

        {mensaje && <div className="equipos-message">{mensaje}</div>}

        <section className="dash-card equipos-card">
          <h3>Crear equipo</h3>

          <div className="equipos-form">
            <input
              placeholder="Ej: Boca Juniors"
              value={nombre}
              onChange={(e) => setNombre(e.target.value)}
            />

            <button onClick={crearEquipo}>
              Crear
            </button>
          </div>
        </section>

        <section className="dash-card equipos-card">
          <h3>Buscar equipo</h3>

          <input
            className="equipos-search"
            placeholder="Buscar equipo..."
            value={busqueda}
            onChange={(e) => {
              setBusqueda(e.target.value);
              fetchEquipos(e.target.value);
            }}
          />
        </section>

        <section className="dash-card equipos-card">
          <h3>Listado de equipos</h3>

          <div className="equipos-list">
            {equipos.length === 0 ? (
              <p className="empty">No hay equipos para mostrar.</p>
            ) : (
              equipos.map((eq) => (
                <div key={eq.idEquipo} className="equipo-card">
                  <span>{eq.nombre}</span>

                  <button onClick={() => eliminarEquipo(eq.idEquipo)}>
                    Eliminar
                  </button>
                </div>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default Equipos;