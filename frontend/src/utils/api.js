const API_URL = "http://localhost:8080/api";

export async function apiFetch(path, options = {}) {
  const token = localStorage.getItem("token");

  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const res = await fetch(`${API_URL}${path}`, {
    ...options,
    headers,
  });

  if (res.status === 204) {
    return null;
  }

  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    throw new Error(
      data?.message ||
      data?.mensaje ||
      "Ocurrió un error en la petición"
    );
  }

  return data;
}