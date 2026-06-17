import { useNavigate } from "react-router-dom";
import "./Landing.css";

function Landing() {

    const navigate = useNavigate();

    return (
        <div className="landing">

            <div className="content">

                <span className="badge">
                     PRODE 2026
                </span>

                <h1>
                    PRODE<br />
                </h1>

                <p className="subtitle">
                    Realizá tus pronósticos, competí contra otros usuarios
                    y escalá posiciones en el ranking.
                </p>

                <div className="buttons">

                    <button
                        className="btn-login"
                        onClick={() => navigate("/login")}
                    >
                        Iniciar Sesión
                    </button>

                    <button
                        className="btn-register"
                        onClick={() => navigate("/register")}
                    >
                        Registrarse
                    </button>

                </div>

            </div>

        </div>
    );
}

export default Landing;