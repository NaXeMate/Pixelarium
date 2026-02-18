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

        <div className="footer-section footer-section-explorar">
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

        <div className="footer-section footer-section-contacto">
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

        <div className="footer-section footer-section-ubicacion">
          <h3>Ubicación</h3>
          <div className="map-placeholder">
            <i className="bi bi-map"></i>
            <p>A Coruña, Galicia</p>
          </div>
        </div>

        {/* Mobile links — visible only at ≤580px */}
        <div className="footer-mobile-links">
          <button
            type="button"
            data-bs-toggle="modal"
            data-bs-target="#explorarModal"
          >
            <i className="bi bi-compass"></i> Explorar
          </button>
          <button
            type="button"
            data-bs-toggle="modal"
            data-bs-target="#contactoModal"
          >
            <i className="bi bi-envelope"></i> Contacto
          </button>
          <button
            type="button"
            data-bs-toggle="modal"
            data-bs-target="#ubicacionModal"
          >
            <i className="bi bi-geo-alt"></i> Ubicación
          </button>
        </div>
      </div>

      {/* Bootstrap Modals */}
      <div
        className="modal fade footer-modal"
        id="explorarModal"
        tabIndex={-1}
        aria-labelledby="explorarModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="explorarModalLabel">
                Explorar
              </h5>
              <button
                type="button"
                className="modal-close-btn"
                data-bs-dismiss="modal"
                aria-label="Cerrar"
              >
                <i className="bi bi-x-lg"></i>
              </button>
            </div>
            <div className="modal-body">
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
          </div>
        </div>
      </div>

      <div
        className="modal fade footer-modal"
        id="contactoModal"
        tabIndex={-1}
        aria-labelledby="contactoModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="contactoModalLabel">
                Contacto
              </h5>
              <button
                type="button"
                className="modal-close-btn"
                data-bs-dismiss="modal"
                aria-label="Cerrar"
              >
                <i className="bi bi-x-lg"></i>
              </button>
            </div>
            <div className="modal-body">
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
          </div>
        </div>
      </div>

      <div
        className="modal fade footer-modal"
        id="ubicacionModal"
        tabIndex={-1}
        aria-labelledby="ubicacionModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="ubicacionModalLabel">
                Ubicación
              </h5>
              <button
                type="button"
                className="modal-close-btn"
                data-bs-dismiss="modal"
                aria-label="Cerrar"
              >
                <i className="bi bi-x-lg"></i>
              </button>
            </div>
            <div className="modal-body">
              <div className="map-placeholder">
                <i className="bi bi-map"></i>
                <p>A Coruña, Galicia</p>
              </div>
            </div>
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
