function Home() {
  const token = localStorage.getItem("token");
  const rol = localStorage.getItem("rol");

  const logout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  return (
    <div>
      <h1>Prode TPI</h1>

      <h2>
        Rol: {rol}
      </h2>

      {rol === "ADMIN" ? (
        <p>Panel de administrador 🔥 (puede gestionar equipos)</p>
      ) : (
        <p>Panel de usuario 👤 (puede hacer pronósticos)</p>
      )}

      <p>Token:</p>

      <textarea rows="5" cols="80" value={token || ""} readOnly />

      <br /><br />

      <button onClick={logout}>
        Cerrar sesión
      </button>
    </div>
  );
}

export default Home;