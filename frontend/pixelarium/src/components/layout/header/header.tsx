import { Link } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext";
import { useCart } from "../../../context/CartContext";
import logo from "../../../assets/logo_brand_image/logo_white_font_no_background.png";
import "./header.css";

export default function Header() {
  const { user, logout } = useAuth();
  const { getTotalItems } = useCart();

  return (
    <header className="header">
      <div className="header-container">
        {/* Logo */}
        <Link to="/" className="header-logo">
          <img src={logo} alt="Pixelarium" className="logo-image" />
        </Link>

        {/* Buscador */}
        <div className="header-search">
          <input
            type="text"
            placeholder="Buscar juegos, accesorios, tecnología..."
            className="search-input"
          />
          <button className="search-button">
            <i className="bi bi-search"></i>
          </button>
        </div>

        {/* Navegación */}
        <nav className="header-nav">
          <Link to="/" className="nav-link">
            Inicio
          </Link>
          <Link to="/products" className="nav-link">
            Productos
          </Link>
        </nav>

        {/* Acciones */}
        <div className="header-actions">
          {/* Carrito */}
          <Link to="/cart" className="cart-button">
            <i className="bi bi-cart3"></i>
            {getTotalItems() > 0 && (
              <span className="cart-badge">{getTotalItems()}</span>
            )}
          </Link>

          {/* Usuario */}
          {user ? (
            <div className="user-menu">
              <span className="user-name">
                <i className="bi bi-person-circle"></i> {user.userName}
              </span>
              <button onClick={logout} className="logout-button">
                <i className="bi bi-box-arrow-right"></i> Cerrar sesión
              </button>
            </div>
          ) : (
            <Link to="/login" className="login-button">
              <i className="bi bi-box-arrow-in-right"></i> Iniciar sesión
            </Link>
          )}
        </div>
      </div>
    </header>
  );
}
