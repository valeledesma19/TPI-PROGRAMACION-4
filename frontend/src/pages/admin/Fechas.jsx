import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import "../Dashboard.css";
import "../AdminCrud.css";

function Fechas() {
  const [fechas, setFechas] = useState([]);
  const [nombre, setNombre] = useState("");
  const [estadoFiltro, setEstadoFiltro] = useState("");
  const [editandoId, setEditandoId] = useState(null);
  const [nombreEditado, setNombreEditado] = useState("");
  const [mensaje, setMensaje] = useState("");

  const cargarFechas = async () => {
    try {
      const query = estadoFiltro ? `?estado=${estadoFiltro}` : "";
      const data = await apiFetch(`/Fecha/Listar${query}`);
      setFechas(data);
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarFechas();
  }, [estadoFiltro]);

  const crearFecha = async () => {
    if (!nombre.trim()) {
      setMensaje("Ingresá un nombre para la fecha.");
      return;
    }

    try {
      await apiFetch("/Fecha/Crear", {
        method: "POST",
        body: JSON.stringify({
          nombre,
          estadoFecha: "PROGRAMADA",
        }),
      });

      setNombre("");
      setMensaje("Fecha creada correctamente.");
      cargarFechas();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const prepararEdicion = (fecha) => {
    setEditandoId(fecha.idFecha);
    setNombreEditado(fecha.nombre);
  };

  const modificarFecha = async () => {
    if (!nombreEditado.trim()) {
      setMensaje("Ingresá el nuevo nombre.");
      return;
    }

    try {
      await apiFetch(`/Fecha/Modificar/${editandoId}`, {
        method: "PUT",
        body: JSON.stringify({
          nombre: nombreEditado,
        }),
      });

      setEditandoId(null);
      setNombreEditado("");
      setMensaje("Fecha modificada correctamente.");
      cargarFechas();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const eliminarFecha = async (id) => {
    const confirmar = confirm("¿Seguro que querés eliminar esta fecha?");
    if (!confirmar) return;

    try {
      await apiFetch(`/Fecha/Eliminar/${id}`, {
        method: "DELETE",
      });

      setMensaje("Fecha eliminada correctamente.");
      cargarFechas();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  return (
    <div className="dashboard-bg">
      <Sidebar type="ADMIN" active="fechas" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Gestión de Fechas</h2>
            <p>Crear, modificar, eliminar y consultar jornadas.</p>
          </div>

          <div className="admin-user">Administrador</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="crud-grid">
          <div className="dash-card crud-card">
            <h3>Crear fecha</h3>

            <div className="crud-form">
              <input
                placeholder="Ej: Fecha 1 - Fase de grupos"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
              />

              <button onClick={crearFecha}>
                Crear fecha
              </button>
            </div>
          </div>

          <div className="dash-card crud-card">
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

                  {editandoId === fecha.idFecha ? (
                    <div className="crud-actions edit-actions">
                      <input
                        value={nombreEditado}
                        onChange={(e) => setNombreEditado(e.target.value)}
                      />

                      <button onClick={modificarFecha}>
                        Guardar
                      </button>

                      <button
                        className="danger"
                        onClick={() => setEditandoId(null)}
                      >
                        Cancelar
                      </button>
                    </div>
                  ) : (
                    <div className="crud-actions">
                      <button onClick={() => prepararEdicion(fecha)}>
                        Editar
                      </button>

                      <button
                        className="danger"
                        onClick={() => eliminarFecha(fecha.idFecha)}
                      >
                        Eliminar
                      </button>
                    </div>
                  )}
                </div>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default Fechas;