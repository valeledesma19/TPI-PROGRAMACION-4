import { useEffect, useState } from "react";
import Sidebar from "../../components/Sidebar";
import { apiFetch } from "../../utils/api";
import "../Dashboard.css";
import "../AdminCrud.css";

function AdminPartidos() {
  const [partidos, setPartidos] = useState([]);
  const [fechas, setFechas] = useState([]);
  const [equipos, setEquipos] = useState([]);
  const [filtroFecha, setFiltroFecha] = useState("");
  const [mensaje, setMensaje] = useState("");

  const [editandoId, setEditandoId] = useState(null);
  const [resultadoId, setResultadoId] = useState(null);

  const [form, setForm] = useState({
    idFecha: "",
    fechaHoraInicio: "",
    idEquipoLocal: "",
    idEquipoVisitante: "",
  });

  const [resultadoForm, setResultadoForm] = useState({
    golesLocal: "",
    golesVisitante: "",
  });

  const cargarDatos = async () => {
    try {
      const query = filtroFecha ? `?idFecha=${filtroFecha}` : "";

      const [partidosData, fechasData, equiposData] = await Promise.all([
        apiFetch(`/partidos${query}`),
        apiFetch("/Fecha/Listar"),
        apiFetch("/equipos"),
      ]);

      setPartidos(partidosData);
      setFechas(fechasData);
      setEquipos(equiposData);
    } catch (error) {
      setMensaje(error.message);
    }
  };

  useEffect(() => {
    cargarDatos();
  }, [filtroFecha]);

  const cambiarForm = (campo, valor) => {
    setForm({
      ...form,
      [campo]: valor,
    });
  };

  const limpiarForm = () => {
    setEditandoId(null);
    setForm({
      idFecha: "",
      fechaHoraInicio: "",
      idEquipoLocal: "",
      idEquipoVisitante: "",
    });
  };

  const limpiarResultado = () => {
    setResultadoId(null);
    setResultadoForm({
      golesLocal: "",
      golesVisitante: "",
    });
  };

  const guardarPartido = async () => {
    if (!form.fechaHoraInicio || !form.idEquipoLocal || !form.idEquipoVisitante) {
      setMensaje("Completá los datos del partido.");
      return;
    }

    if (!editandoId && !form.idFecha) {
      setMensaje("Seleccioná una fecha.");
      return;
    }

    if (form.idEquipoLocal === form.idEquipoVisitante) {
      setMensaje("El equipo local y visitante no pueden ser el mismo.");
      return;
    }

    try {
      if (editandoId) {
        await apiFetch(`/partidos/${editandoId}`, {
          method: "PUT",
          body: JSON.stringify({
            fechaHoraInicio: form.fechaHoraInicio,
            idEquipoLocal: Number(form.idEquipoLocal),
            idEquipoVisitante: Number(form.idEquipoVisitante),
          }),
        });

        setMensaje("Partido modificado correctamente.");
      } else {
        await apiFetch("/partidos", {
          method: "POST",
          body: JSON.stringify({
            idFecha: Number(form.idFecha),
            fechaHoraInicio: form.fechaHoraInicio,
            idEquipoLocal: Number(form.idEquipoLocal),
            idEquipoVisitante: Number(form.idEquipoVisitante),
          }),
        });

        setMensaje("Partido creado correctamente.");
      }

      limpiarForm();
      cargarDatos();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const prepararEdicion = (partido) => {
    setEditandoId(partido.idPartido);
    setResultadoId(null);

    setForm({
      idFecha: partido.idFecha,
      fechaHoraInicio: partido.fechaHoraInicio?.slice(0, 16),
      idEquipoLocal: partido.idEquipoLocal,
      idEquipoVisitante: partido.idEquipoVisitante,
    });
  };

  const pasarAEnJuego = async (id) => {
    try {
      await apiFetch(`/partidos/${id}/en-juego`, {
        method: "PATCH",
      });

      setMensaje("Partido cambiado a EN JUEGO.");
      cargarDatos();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const prepararResultado = (partido) => {
    setResultadoId(partido.idPartido);
    setEditandoId(null);

    setResultadoForm({
      golesLocal: partido.golesLocal ?? "",
      golesVisitante: partido.golesVisitante ?? "",
    });
  };

  const cargarResultado = async () => {
    if (resultadoForm.golesLocal === "" || resultadoForm.golesVisitante === "") {
      setMensaje("Completá los goles reales de ambos equipos.");
      return;
    }

    try {
      await apiFetch(`/partidos/${resultadoId}/resultado`, {
        method: "PUT",
        body: JSON.stringify({
          golesLocal: Number(resultadoForm.golesLocal),
          golesVisitante: Number(resultadoForm.golesVisitante),
        }),
      });

      setMensaje("Resultado cargado correctamente. El partido finalizó y se calcularon los puntos.");
      limpiarResultado();
      cargarDatos();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  const eliminarPartido = async (id) => {
    const confirmar = confirm("¿Seguro que querés eliminar este partido?");
    if (!confirmar) return;

    try {
      await apiFetch(`/partidos/${id}`, {
        method: "DELETE",
      });

      setMensaje("Partido eliminado correctamente.");
      cargarDatos();
    } catch (error) {
      setMensaje(error.message);
    }
  };

  return (
    <div className="dashboard-bg">
      <Sidebar type="ADMIN" active="partidos" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Gestión de Partidos</h2>
            <p>Crear, modificar, eliminar, iniciar partidos y cargar resultados.</p>
          </div>

          <div className="admin-user">Administrador</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="dash-card crud-card">
          <h3>{editandoId ? "Modificar partido" : "Crear partido"}</h3>

          <div className="crud-form form-grid">
            {!editandoId && (
              <select
                value={form.idFecha}
                onChange={(e) => cambiarForm("idFecha", e.target.value)}
              >
                <option value="">Seleccionar fecha</option>

                {fechas.map((fecha) => (
                  <option key={fecha.idFecha} value={fecha.idFecha}>
                    {fecha.nombre} - {fecha.estado}
                  </option>
                ))}
              </select>
            )}

            <input
              type="datetime-local"
              value={form.fechaHoraInicio}
              onChange={(e) => cambiarForm("fechaHoraInicio", e.target.value)}
            />

            <select
              value={form.idEquipoLocal}
              onChange={(e) => cambiarForm("idEquipoLocal", e.target.value)}
            >
              <option value="">Equipo local</option>

              {equipos.map((equipo) => (
                <option key={equipo.idEquipo} value={equipo.idEquipo}>
                  {equipo.nombre}
                </option>
              ))}
            </select>

            <select
              value={form.idEquipoVisitante}
              onChange={(e) => cambiarForm("idEquipoVisitante", e.target.value)}
            >
              <option value="">Equipo visitante</option>

              {equipos.map((equipo) => (
                <option key={equipo.idEquipo} value={equipo.idEquipo}>
                  {equipo.nombre}
                </option>
              ))}
            </select>

            <button onClick={guardarPartido}>
              {editandoId ? "Guardar cambios" : "Crear partido"}
            </button>

            {editandoId && (
              <button className="danger" onClick={limpiarForm}>
                Cancelar
              </button>
            )}
          </div>
        </section>

        {resultadoId && (
          <section className="dash-card crud-card">
            <h3>Cargar resultado real</h3>
            <p>Solo se puede cargar resultado si el partido está EN_JUEGO.</p>

            <div className="crud-form form-grid">
              <input
                type="number"
                min="0"
                placeholder="Goles local"
                value={resultadoForm.golesLocal}
                onChange={(e) =>
                  setResultadoForm({
                    ...resultadoForm,
                    golesLocal: e.target.value,
                  })
                }
              />

              <input
                type="number"
                min="0"
                placeholder="Goles visitante"
                value={resultadoForm.golesVisitante}
                onChange={(e) =>
                  setResultadoForm({
                    ...resultadoForm,
                    golesVisitante: e.target.value,
                  })
                }
              />

              <button onClick={cargarResultado}>
                Guardar resultado
              </button>

              <button className="danger" onClick={limpiarResultado}>
                Cancelar
              </button>
            </div>
          </section>
        )}

        <section className="dash-card crud-card">
          <h3>Filtrar partidos por fecha</h3>

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
                        Resultado: {p.golesLocal} - {p.golesVisitante} | Tendencia: {p.tendenciaResultado}
                      </p>
                    )}
                  </div>

                  <div className="crud-actions">
                    {p.estado === "POR_JUGARSE" && (
                      <>
                        <button onClick={() => prepararEdicion(p)}>
                          Editar
                        </button>

                        <button onClick={() => pasarAEnJuego(p.idPartido)}>
                          Pasar a en juego
                        </button>

                        <button
                          className="danger"
                          onClick={() => eliminarPartido(p.idPartido)}
                        >
                          Eliminar
                        </button>
                      </>
                    )}

                    {p.estado === "EN_JUEGO" && (
                      <button onClick={() => prepararResultado(p)}>
                        Cargar resultado
                      </button>
                    )}

                    {p.estado === "FINALIZADO" && (
                      <span className="estado-pronostico">
                        Finalizado
                      </span>
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

export default AdminPartidos;