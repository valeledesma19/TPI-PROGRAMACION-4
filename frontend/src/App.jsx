import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "./pages/Landing";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";

import Admin from "./pages/Admin";
import User from "./pages/User";
import Equipos from "./pages/admin/Equipos";
import Pronosticos from "./pages/user/Pronosticos";

import ProtectedRoute from "./components/ProtectedRoute";
import PronosticosTerceros from "./pages/user/PronosticosTerceros";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />

        {/* ADMIN */}
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

        {/* USER */}
        <Route
          path="/user"
          element={
            <ProtectedRoute role="USER">
              <User />
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