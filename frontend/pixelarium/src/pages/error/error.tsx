import { useRouteError } from "react-router-dom";
import Button from "../../components/common/button/button";
import "./error.css";

export default function Error() {
  const error = useRouteError() as {
    status?: number;
    statusText?: string;
    message?: string;
  };

  return (
    <div className="error-page">
      <div className="error-page__icon">
        <i className="bi bi-exclamation-triangle"></i>
      </div>
      <h1 className="error-page__title">¡Algo salió mal!</h1>
      <p className="error-page__message">
        {error?.statusText ||
          error?.message ||
          "Ha ocurrido un error inesperado."}
      </p>
      <div className="error-page__actions">
        <Button to="/" icon="bi-house-door">
          Volver al Inicio
        </Button>
        <Button to="/products" variant="secondary" icon="bi-grid">
          Ver Productos
        </Button>
      </div>
    </div>
  );
}
