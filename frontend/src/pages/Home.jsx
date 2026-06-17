import { Navigate } from "react-router-dom";

function Home() {
  const rol = localStorage.getItem("rol");

  if (rol === "ADMIN") {
    return <Navigate to="/admin" />;
  }

  if (rol === "USER") {
    return <Navigate to="/user" />;
  }

  return <Navigate to="/login" />;
}

export default Home;