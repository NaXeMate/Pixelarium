import logo from "../../../assets/logo_brand_image/favicon_No_Background.png";
import "./footer.css";

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-section footer-brand">
          <div className="footer-logo">
            <img src={logo} alt="Pixelarium" className="footer-logo-icon" />
          </div>
          <p className="footer-description">
            Tu destino número uno para gaming, tech y lo último en innovación
            del mercado. Calidad y pasión en cada producto.
          </p>
          <div className="social-links">
            <a
              href="https://facebook.com"
              target="_blank"
              rel="noopener noreferrer"
              className="social-icon"
            >
              <i className="bi bi-facebook"></i>
            </a>
            <a
              href="https://instagram.com"
              target="_blank"
              rel="noopener noreferrer"
              className="social-icon"
            >
              <i className="bi bi-instagram"></i>
            </a>
            <a
              href="https://twitter.com"
              target="_blank"
              rel="noopener noreferrer"
              className="social-icon"
            >
              <i className="bi bi-twitter-x"></i>
            </a>
            <a
              href="https://youtube.com"
              target="_blank"
              rel="noopener noreferrer"
              className="social-icon"
            >
              <i className="bi bi-youtube"></i>
            </a>
          </div>
        </div>

        <div className="footer-section">
          <h3>Explorar</h3>
          <ul>
            <li>
              <a href="/">Inicio</a>
            </li>
            <li>
              <a href="/products">Catálogo Completo</a>
            </li>
            <li>
              <a href="/products?sale=true">Ofertas Especiales</a>
            </li>
            <li>
              <a href="#">Próximos Lanzamientos</a>
            </li>
            <li>
              <a href="#">Blog Tech</a>
            </li>
          </ul>
        </div>

        <div className="footer-section">
          <h3>Contacto</h3>
          <ul className="contact-info">
            <li>
              <i className="bi bi-geo-alt-fill"></i>
              C/Tecnológica 123, A Coruña
            </li>
            <li>
              <i className="bi bi-envelope-fill"></i>
              info@pixelarium.com
            </li>
            <li>
              <i className="bi bi-telephone-fill"></i>
              +34 981 123 456
            </li>
          </ul>
        </div>

        <div className="footer-section">
          <h3>Ubicación</h3>
          <div className="map-placeholder">
            <i className="bi bi-map"></i>
            <p>A Coruña, Galicia</p>
          </div>
        </div>
      </div>

      <div className="footer-bottom">
        <p>© 2026 Pixelarium. Todos los derechos reservados.</p>
        <div className="footer-links">
          <a href="#">Privacidad</a>
          <a href="#">Términos</a>
          <a href="#">Cookies</a>
        </div>
      </div>
    </footer>
  );
}
