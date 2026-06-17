import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Auth.css";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async () => {
    setError("");

    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!res.ok) {
        setError("Credenciales inválidas");
        return;
      }

      const data = await res.json();

      localStorage.setItem("token", data.token);
      localStorage.setItem("usuarioId", data.idUsuario);
      localStorage.setItem("email", data.email);
      localStorage.setItem("rol", data.rol);

      if (data.rol === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/user");
      }

    } catch {
      setError("Error de conexión con el servidor");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">

        <h1>Iniciar Sesión</h1>

        {error && (
          <p className="auth-error">
            {error}
          </p>
        )}

        <input
          type="email"
          placeholder="Correo electrónico"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin}>
          Ingresar
        </button>

      </div>
    </div>
  );
}

export default Login;