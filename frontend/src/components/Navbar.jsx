import { useNavigate } from "react-router-dom";
import { getUserFromToken } from "../utils/auth";


function Navbar() {
  const navigate = useNavigate();
  const user = getUserFromToken();

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div style={{ padding: 10, borderBottom: "1px solid #ccc" }}>
      <button onClick={() => navigate("/home")}>Home</button>

      {user?.rol === "ADMIN" && (
        <>
          <button onClick={() => navigate("/admin")}>Admin</button>
          <button onClick={() => navigate("/admin/equipos")}>Equipos</button>
        </>
      )}

      {user?.rol === "USER" && (
        <button onClick={() => navigate("/user")}>Usuario</button>
      )}

      <button onClick={logout}>Logout</button>
    </div>
  );
}

export default Navbar;