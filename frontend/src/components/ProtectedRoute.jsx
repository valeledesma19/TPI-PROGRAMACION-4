import { Navigate } from "react-router-dom";
import { getUserFromToken } from "../utils/auth";

function ProtectedRoute({ children, role }) {
  const user = getUserFromToken();

  if (!user) {
    return <Navigate to="/login" />;
  }

  if (role && user.rol !== role) {
    if (user.rol === "ADMIN") {
      return <Navigate to="/admin" />;
    }

    if (user.rol === "USER") {
      return <Navigate to="/user" />;
    }

    return <Navigate to="/login" />;
  }

  return children;
}

export default ProtectedRoute;