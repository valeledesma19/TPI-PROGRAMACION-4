const API_URL = "http://localhost:8080/api/auth";

export const login = async (email, password) => {

    const response = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email,
            password
        })
    });

    if (!response.ok) {
        throw new Error("Credenciales inválidas");
    }

    return await response.json();
};

export const register = async (nombre, email, password) => {

    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nombre,
            email,
            password
        })
    });

    if (!response.ok) {
        throw new Error("Error al registrarse");
    }

    return await response.json();
};