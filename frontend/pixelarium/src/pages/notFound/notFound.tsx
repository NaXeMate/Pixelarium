import Button from "../../components/common/button/button";
import "./notFound.css";

export default function NotFound() {
  return (
    <div className="not-found">
      <div className="not-found__icon">
        <i className="bi bi-emoji-frown"></i>
      </div>
      <h1 className="not-found__code">404</h1>
      <p className="not-found__title">Página no encontrada</p>
      <p className="not-found__message">
        La página que buscas no existe o ha sido movida.
      </p>
      <div className="not-found__actions">
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
