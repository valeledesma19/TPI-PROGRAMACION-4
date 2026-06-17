import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "./pages/Landing";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";

import Admin from "./pages/Admin";
import User from "./pages/User";

import Equipos from "./pages/admin/Equipos";
import Fechas from "./pages/admin/Fechas";
import AdminPartidos from "./pages/admin/AdminPartidos";

import UserPartidos from "./pages/user/UserPartidos.jsx";
import Pronosticos from "./pages/user/Pronosticos";
import PronosticosTerceros from "./pages/user/PronosticosTerceros";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />

        <Route
          path="/admin"
          element={
            <ProtectedRoute role="ADMIN">
              <Admin />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/equipos"
          element={
            <ProtectedRoute role="ADMIN">
              <Equipos />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/fechas"
          element={
            <ProtectedRoute role="ADMIN">
              <Fechas />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/partidos"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminPartidos />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user"
          element={
            <ProtectedRoute role="USER">
              <User />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/partidos"
          element={
            <ProtectedRoute role="USER">
              <UserPartidos />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/pronosticos"
          element={
            <ProtectedRoute role="USER">
              <Pronosticos />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/pronosticos-terceros"
          element={
            <ProtectedRoute role="USER">
              <PronosticosTerceros />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;