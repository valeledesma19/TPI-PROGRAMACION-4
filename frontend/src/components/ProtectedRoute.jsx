import { Navigate } from "react-router-dom";
import { getUserFromToken } from "../utils/auth";

function ProtectedRoute({ children, role }) {
  const user = getUserFromToken();

  if (!user) {
    return <Navigate to="/login" />;
  }

  if (role && user.rol !== role) {
    return <Navigate to="/home" />;
  }

  return children;
}

export default ProtectedRoute;