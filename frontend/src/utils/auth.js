export function getUserFromToken() {
  const token = localStorage.getItem("token");

  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));

    return {
      ...payload,
      idUsuario: localStorage.getItem("usuarioId"),
      rol: payload.rol || localStorage.getItem("rol"),
    };
  } catch {
    return null;
  }
}

export function logout(navigate) {
  localStorage.clear();
  navigate("/login");
}
