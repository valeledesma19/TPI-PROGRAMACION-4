<img width="1667" height="1358" alt="Copia de TPI - PROGIV" src="https://github.com/user-attachments/assets/1aaffe77-102e-40dd-b0fa-8a75f4c88e95" />

<img width="2480" height="915" alt="Diagrama en blanco (3)" src="https://github.com/user-attachments/assets/ddc1786f-ee7d-44e0-b46f-e60e740d1128" />


# ⚽ Prode Deportivo

Sistema web de predicciones deportivas donde los usuarios pueden realizar pronósticos sobre partidos, consultar resultados y competir dentro de un ranking según sus aciertos.

El sistema permite administrar partidos, equipos y resultados, además de gestionar las predicciones de los usuarios aplicando reglas de negocio como el cierre de pronósticos antes del inicio del partido.

Proyecto desarrollado como aplicación Full Stack utilizando **Java + Spring Boot** para el backend y **React + Vite** para el frontend.

---

# 🚀 Funcionalidades

## 👤 Usuarios

- Registro e inicio de sesión.
- Autenticación mediante JWT.
- Visualización de partidos disponibles.
- Carga de predicciones.
- Consulta de predicciones realizadas.
- Visualización de resultados de partidos.
- Sistema de puntuación según aciertos.

---

## 🔐 Administradores

- Gestión de equipos.
- Gestión de fechas y partidos.
- Carga de resultados finales.
- Administración del estado de los partidos.
- Control de información del torneo.

---

# ⚙️ Reglas de negocio implementadas

- Los usuarios pueden realizar predicciones únicamente antes del horario límite establecido.
- Las predicciones se bloquean 30 minutos antes del comienzo del partido.
- Un usuario no puede modificar una predicción una vez cerrada.
- Los partidos manejan diferentes estados:

```
POR JUGARSE
EN JUEGO
FINALIZADO
```

- No se permiten partidos duplicados entre los mismos equipos en la misma fecha.
- Los resultados son cargados únicamente por administradores.
- Las predicciones se utilizan para calcular los puntos obtenidos por cada usuario.

---

# 🛠️ Tecnologías utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- BCrypt
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

## Frontend

- React
- Vite
- JavaScript
- React Router
- Fetch API
- CSS

## Herramientas

- IntelliJ IDEA
- Visual Studio Code
- Postman
- Git
- GitHub
- Neon PostgreSQL 

---

# 🏗️ Arquitectura del proyecto

El backend está organizado siguiendo una arquitectura por capas:

```
backend
│
├── controller
│
├── service
│
├── repository
│
├── entity
│
├── dto
│
└── security
```

Frontend:

```
frontend
│
├── components
│
├── pages
│
├── services
│
├── context
│
└── utils
```

---

# 🔒 Seguridad

El sistema implementa autenticación mediante JWT.

Características:

- Inicio de sesión seguro.
- Tokens JWT para validar usuarios.
- Contraseñas almacenadas utilizando BCrypt.
- Protección de endpoints mediante Spring Security.
- Control de acceso según roles.

Roles disponibles:

```
USER
ADMIN
```

---

# 🗄️ Base de datos

La aplicación utiliza PostgreSQL como sistema gestor de base de datos.

Principales entidades:

- Usuario
- Rol
- Equipo
- Partido
- Fecha
- Predicción
- Resultado


Relaciones principales:

```
Usuario 1 -------- N Predicción

Partido 1 -------- N Predicción

Equipo 1 -------- N Partido
```

---

# ⚽ Funcionamiento general

1. El administrador crea las fechas y partidos del torneo.
2. Los usuarios ingresan al sistema.
3. Cada usuario realiza sus predicciones antes del cierre.
4. Una vez iniciado el partido, las predicciones quedan bloqueadas.
5. El administrador carga el resultado final.
6. El sistema compara las predicciones con los resultados y calcula los puntos.

---

# ⚙️ Instalación y ejecución

## Requisitos

- Java 21
- Node.js
- PostgreSQL
- Maven

---

# Backend

Clonar repositorio:

```bash
git clone https://github.com/valeledesma19/prode.git
```

Ingresar a la carpeta backend:

```bash
cd backend
```

Configurar las variables de conexión a PostgreSQL.

Ejecutar:

```bash
mvn spring-boot:run
```

Backend disponible en:

```
http://localhost:8080
```

---

# Frontend

Ingresar a la carpeta frontend:

```bash
cd frontend
```

Instalar dependencias:

```bash
npm install
```

Ejecutar:

```bash
npm run dev
```

Frontend disponible en:

```
http://localhost:5173
```

---

# 📌 Endpoints principales

## Autenticación

```
POST /api/auth/register

POST /api/auth/login
```

---

## Equipos

```
GET /api/equipos

POST /api/equipos

PUT /api/equipos/{id}

DELETE /api/equipos/{id}
```

---

## Partidos

```
GET /api/partidos

POST /api/partidos

PUT /api/partidos/{id}
```

---

## Predicciones

```
GET /api/predicciones

POST /api/predicciones
```

---

# 🧪 Testing

Se realizaron pruebas utilizando:

- Postman para validar endpoints.
- Pruebas de registro e inicio de sesión.
- Validación de permisos según rol.
- Pruebas de creación de partidos.
- Validación de restricciones de predicciones.
- Prueba de bloqueo de pronósticos antes del inicio del partido.

---


Base de datos:

- Neon PostgreSQL

