import { useEffect, useState } from "react";
import { getUserFromToken } from "../../utils/auth";
import "./equipos.css";
function Equipos() {
  const [equipos, setEquipos] = useState([]);
  const [nombre, setNombre] = useState("");
  const [busqueda, setBusqueda] = useState("");

  const token = localStorage.getItem("token");
  const user = getUserFromToken();

  const API = "http://localhost:8080/api/equipos";

  const fetchEquipos = async (filtro = "") => {
    const url = filtro ? `${API}?nombre=${filtro}` : API;

    const res = await fetch(url, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) return;

    const data = await res.json();
    setEquipos(data);
  };

  useEffect(() => {
    fetchEquipos();
  }, []);

  const crearEquipo = async () => {
    if (!nombre.trim()) return;

    await fetch(API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ nombre }),
    });

    setNombre("");
    fetchEquipos();
  };

  const eliminarEquipo = async (id) => {
    await fetch(`${API}/${id}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });

    fetchEquipos();
  };

  if (user?.rol !== "ADMIN") {
    return <h2>No tenés permisos</h2>;
  }

  return (
    <div className="equipos-container">

      <h1>Gestión de Equipos</h1>

      {/* CREAR */}
      <div className="equipos-form">
        <input
          placeholder="Ej: Boca Juniors"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
        />
        <button onClick={crearEquipo}>Crear</button>
      </div>

      {/* BUSCAR */}
      <input
        className="equipos-search"
        placeholder="Buscar equipo..."
        value={busqueda}
        onChange={(e) => {
          setBusqueda(e.target.value);
          fetchEquipos(e.target.value);
        }}
      />

      {/* LISTA */}
      <div className="equipos-list">
        {equipos.map((eq) => (
          <div key={eq.idEquipo} className="equipo-card">
            <span>{eq.nombre}</span>

            <button onClick={() => eliminarEquipo(eq.idEquipo)}>
              ❌
            </button>
          </div>
        ))}
      </div>

    </div>
  );
}

export default Equipos;