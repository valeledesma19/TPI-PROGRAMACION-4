<img width="1667" height="1358" alt="Copia de TPI - PROGIV" src="https://github.com/user-attachments/assets/1aaffe77-102e-40dd-b0fa-8a75f4c88e95" />

<img width="2480" height="915" alt="Diagrama en blanco (3)" src="https://github.com/user-attachments/assets/ddc1786f-ee7d-44e0-b46f-e60e740d1128" />


# 🏟️ Sports Booking Platform

Sistema web Full Stack para la gestión y reserva de canchas deportivas.

La aplicación permite a los usuarios consultar canchas disponibles, seleccionar horarios, realizar reservas y administrar sus turnos. Además, cuenta con un panel administrativo donde se pueden gestionar canchas y reservas.

El proyecto fue desarrollado utilizando **Java con Spring Boot** para el backend y **React con Vite** para el frontend, implementando autenticación segura mediante JWT y una base de datos PostgreSQL.

---

# 🚀 Funcionalidades

## 👤 Usuario

- Registro de usuarios.
- Inicio de sesión.
- Autenticación mediante JWT.
- Consulta de canchas disponibles.
- Visualización de horarios libres.
- Creación de reservas.
- Consulta de reservas realizadas.
- Cancelación de reservas propias.

## 🔐 Administrador

- Inicio de sesión administrativo.
- Gestión de canchas deportivas.
- Crear nuevas canchas.
- Modificar información de canchas.
- Eliminar canchas.
- Visualización de reservas.
- Administración general del sistema.

---

# 🛠️ Tecnologías utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- BCrypt para encriptación de contraseñas
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger

## Frontend

- React
- Vite
- JavaScript
- React Router
- Axios / Fetch API
- CSS

## Herramientas utilizadas

- IntelliJ IDEA
- Visual Studio Code
- Git
- GitHub
- Postman
- Render
- Vercel

---

# 🏗️ Arquitectura del proyecto

El backend está desarrollado siguiendo una arquitectura por capas:

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

El frontend está organizado de la siguiente manera:

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

La aplicación implementa autenticación y autorización mediante JWT.

Características:

- Login seguro.
- Tokens JWT para mantener la sesión.
- Contraseñas protegidas mediante BCrypt.
- Control de acceso mediante roles.

Roles disponibles:

```
USER
ADMIN
```

Los endpoints están protegidos utilizando Spring Security.

---

# 📅 Sistema de reservas

El sistema cuenta con validaciones para evitar conflictos entre reservas.

Antes de crear una reserva se verifica:

- Cancha seleccionada.
- Fecha elegida.
- Horario disponible.
- Existencia de una reserva previa.

Los horarios ocupados no pueden volver a ser seleccionados por otros usuarios.

---

# 🗄️ Base de datos

La aplicación utiliza PostgreSQL.

Principales entidades:

- Usuario
- Rol
- Cancha
- Deporte
- Reserva
- Horario


Relaciones principales:

```
Usuario 1 -------- N Reserva

Cancha 1 -------- N Reserva

Deporte 1 -------- N Cancha
```

---

# ⚙️ Instalación y ejecución

## Requisitos

Antes de ejecutar el proyecto es necesario tener instalado:

- Java 21
- Node.js
- PostgreSQL
- Maven

---

# Backend

Clonar el repositorio:

```bash
git clone https://github.com/valeledesma19/sports-booking-platform.git
```

Ingresar al backend:

```bash
cd backend
```

Configurar las variables de conexión a PostgreSQL.

Ejecutar el proyecto:

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

Ejecutar aplicación:

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

## Canchas

```
GET /api/canchas

POST /api/canchas

PUT /api/canchas/{id}

DELETE /api/canchas/{id}
```

---

## Reservas

```
GET /api/reservas

POST /api/reservas

DELETE /api/reservas/{id}
```

---

# 🧪 Testing

Las pruebas del sistema fueron realizadas utilizando:

- Postman para validar endpoints.
- Pruebas de autenticación.
- Validación de creación de reservas.
- Validación de horarios ocupados.
- Verificación de restricciones para evitar reservas duplicadas.

---



# 🔮 Mejoras futuras

- Integración con pagos online.
- Calendario semanal interactivo.
- Notificaciones por correo electrónico.
- Recuperación de contraseña.
- Sistema de favoritos.
- Valoraciones y comentarios de canchas.
- Dashboard administrativo con estadísticas.

---

# 📄 Licencia

Proyecto desarrollado con fines educativos y como proyecto personal de portfolio.

---


