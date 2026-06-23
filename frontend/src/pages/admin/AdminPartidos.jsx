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

  const [partidoAbiertoId, setPartidoAbiertoId] = useState(null);
  const [pronosticosPorPartido, setPronosticosPorPartido] = useState({});

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

  const puedePasarAEnJuego = (partido) => {
    if (!partido.fechaHoraInicio) return false;

    const ahora = new Date();
    const fechaPartido = new Date(partido.fechaHoraInicio);

    return ahora >= fechaPartido;
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
        const confirmar = confirm(
          "Si modificás este partido, se eliminarán todos los pronósticos cargados para este partido. ¿Querés continuar?"
        );

        if (!confirmar) return;

        await apiFetch(`/partidos/${editandoId}`, {
          method: "PUT",
          body: JSON.stringify({
            fechaHoraInicio: form.fechaHoraInicio,
            idEquipoLocal: Number(form.idEquipoLocal),
            idEquipoVisitante: Number(form.idEquipoVisitante),
          }),
        });

        setMensaje("Partido modificado correctamente. Los pronósticos anteriores fueron eliminados.");
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

  const pasarAEnJuego = async (partido) => {
    if (!puedePasarAEnJuego(partido)) {
      setMensaje("No se puede pasar a EN JUEGO antes de la fecha y hora programada.");
      return;
    }

    try {
      await apiFetch(`/partidos/${partido.idPartido}/en-juego`, {
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

      setMensaje("Resultado cargado correctamente. El partido finalizó y no podrá corregirse.");
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

  const togglePronosticos = async (partido) => {
    if (partidoAbiertoId === partido.idPartido) {
      setPartidoAbiertoId(null);
      return;
    }

    setPartidoAbiertoId(partido.idPartido);

    if (pronosticosPorPartido[partido.idPartido]) {
      return;
    }

    try {
      const data = await apiFetch(`/pronosticos/admin/partido/${partido.idPartido}`);

      setPronosticosPorPartido({
        ...pronosticosPorPartido,
        [partido.idPartido]: data,
      });

      setMensaje("");
    } catch (error) {
      setPronosticosPorPartido({
        ...pronosticosPorPartido,
        [partido.idPartido]: [],
      });

      setMensaje(error.message);
    }
  };

  const fechasProgramadas = fechas.filter(
    (fecha) => fecha.estado === "PROGRAMADA"
  );

  return (
    <div className="dashboard-bg">
      <Sidebar type="ADMIN" active="partidos" />

      <main className="dashboard-main">
        <header className="dashboard-header">
          <div>
            <h2>Gestión de Partidos</h2>
            <p>Crear, modificar, eliminar, iniciar partidos, cargar resultados y ver pronósticos.</p>
          </div>

          <div className="admin-user">Administrador</div>
        </header>

        {mensaje && <div className="crud-message">{mensaje}</div>}

        <section className="dash-card crud-card">
          <h3>{editandoId ? "Modificar partido" : "Crear partido"}</h3>

          {editandoId && (
            <p className="empty">
              Atención: al modificar este partido se eliminarán los pronósticos existentes.
            </p>
          )}

          <div className="crud-form form-grid">
            {!editandoId && (
              <>
                <select
                  value={form.idFecha}
                  onChange={(e) => cambiarForm("idFecha", e.target.value)}
                >
                  <option value="">Seleccionar fecha</option>

                  {fechasProgramadas.map((fecha) => (
                    <option key={fecha.idFecha} value={fecha.idFecha}>
                      {fecha.nombre} - {fecha.estado}
                    </option>
                  ))}
                </select>

                {fechasProgramadas.length === 0 && (
                  <p className="empty">
                    No hay fechas programadas disponibles para crear partidos.
                  </p>
                )}
              </>
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

            <p className="empty">
              Una vez cargado el resultado, el partido quedará FINALIZADO y no podrá corregirse.
            </p>

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
              partidos.map((p) => {
                const estaAbierto = partidoAbiertoId === p.idPartido;
                const pronosticos = pronosticosPorPartido[p.idPartido] || [];

                return (
                  <div className="crud-item partido-item" key={p.idPartido}>
                    <div className="partido-row">
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

                        {p.estado === "POR_JUGARSE" && !puedePasarAEnJuego(p) && (
                          <p>
                            No se puede pasar a EN JUEGO hasta la fecha y hora programada.
                          </p>
                        )}

                        {p.estado === "FINALIZADO" && (
                          <p>
                            Resultado: {p.golesLocal} - {p.golesVisitante} | Tendencia: {p.tendenciaResultado}
                          </p>
                        )}
                      </div>

                      <div className="crud-actions">
                        <button
                          className={`pronosticos-btn ${estaAbierto ? "open" : ""}`}
                          onClick={() => togglePronosticos(p)}
                        >
                          <span className="pronosticos-icon">
                            {estaAbierto ? "⌄" : "›"}
                          </span>

                          <span>
                            {estaAbierto ? "Ocultar" : "Ver"} pronósticos
                          </span>

                          {pronosticosPorPartido[p.idPartido] && (
                            <span className="pronosticos-count">
                              {pronosticosPorPartido[p.idPartido].length}
                            </span>
                          )}
                        </button>

                        {p.estado === "POR_JUGARSE" && (
                          <>
                            <button onClick={() => prepararEdicion(p)}>
                              Editar
                            </button>

                            {puedePasarAEnJuego(p) && (
                              <button onClick={() => pasarAEnJuego(p)}>
                                Pasar a en juego
                              </button>
                            )}

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

                    {estaAbierto && (
                      <div className="pronosticos-inline">
                        <h4>Pronósticos cargados</h4>

                        {pronosticos.length === 0 ? (
                          <p className="empty">
                            Este partido todavía no tiene pronósticos.
                          </p>
                        ) : (
                          pronosticos.map((pronostico) => (
                            <div
                              className="pronostico-inline-card"
                              key={pronostico.idPronostico}
                            >
                              <div>
                                <strong>
                                  {pronostico.nombreUsuario || `Usuario #${pronostico.usuarioId}`}
                                </strong>

                                <p>
                                  Fecha de carga:{" "}
                                  {pronostico.fechaCreacion?.replace("T", " ")}
                                </p>
                              </div>

                              <div className="pronostico-inline-resultado">
                                <span>
                                  {pronostico.golesLocalPredicho} -{" "}
                                  {pronostico.golesVisitantePredicho}
                                </span>

                                <span>
                                  {pronostico.puntosObtenidos ?? 0} pts
                                </span>
                              </div>
                            </div>
                          ))
                        )}
                      </div>
                    )}
                  </div>
                );
              })
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default AdminPartidos;