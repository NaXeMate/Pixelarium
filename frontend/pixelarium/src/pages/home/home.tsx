import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Button from "../../components/common/button/button";
import Header from "../../components/layout/header/header";
import Footer from "../../components/layout/footer/footer";
import { getSaleOffers } from "../../services/productService";
import type { ProductResponse } from "../../types";
import ProductCard from "../../components/layout/productCard/productCard";
import "./home.css";

/* ── Static data ── */

const categories = [
  { label: "Switch", icon: "bi-nintendo-switch", query: "NINTENDO_SWITCH" },
  {
    label: "Switch 2",
    icon: "bi-controller",
    query: "NINTENDO_SWITCH_2",
  },
  { label: "PC", icon: "bi-pc-display", query: "PC" },
  { label: "Apple", icon: "bi-apple", query: "APPLE" },
  { label: "Accesorios", icon: "bi-usb-plug", query: "ACCESSORIES" },
];

/* ── Helpers ── */

/* ── Component ── */

export default function Home() {
  const [featuredProducts, setFeaturedProducts] = useState<ProductResponse[]>(
    [],
  );
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadFeatured() {
      try {
        const products = await getSaleOffers();
        // Just take the first 4 for the home page
        setFeaturedProducts(products.slice(0, 4));
      } catch (error) {
        console.error("Error fetching featured products:", error);
      } finally {
        setLoading(false);
      }
    }
    loadFeatured();
  }, []);

  return (
    <div className="home-page">
      <Header />

      <main className="home-content">
        {/* ======== HERO ======== */}
        <section className="hero">
          <div className="hero-inner">
            <div className="hero-text">
              <span className="hero-badge">
                <span className="hero-badge-dot" /> NUEVA COLECCIÓN 2026
              </span>

              <h1 className="hero-title">
                Sube de Nivel <br />
                <span className="hero-accent">Tu Realidad.</span>
              </h1>

              <p className="hero-subtitle">
                Descubre lo último en tecnología para gaming, los accesorios más
                vendidos y los gadgets que transformarán tu día a día.
                Bienvenido a la nueva era de Pixelarium.
              </p>

              <div className="hero-actions">
                <Button to="/products" variant="primary" icon="bi-cart3">
                  Comprar Ahora
                </Button>
                <Button to="/products" variant="secondary" icon="bi-stars">
                  Ver Novedades
                </Button>
              </div>

              <div className="hero-trust">
                <span>
                  <i className="bi bi-truck" /> Envío Gratis
                </span>
                <span>
                  <i className="bi bi-shield-check" /> Garantía Oficial
                </span>
                <span>
                  <i className="bi bi-headset" /> Soporte 24/7
                </span>
              </div>
            </div>

            <div className="hero-visual">
              <img
                src="/products/pro_controller.png"
                alt="Nuevo mando gaming"
                className="hero-image"
              />

              <div className="hero-floating-card">
                <span className="floating-label">
                  <i className="bi bi-fire" /> TRENDING NOW
                </span>
                <span className="floating-name">
                  Nintendo Switch Pro Controller
                </span>
              </div>
            </div>
          </div>
        </section>

        {/* ======== CATEGORIES ======== */}
        <section className="categories">
          <div className="categories-inner">
            {categories.map((cat) => (
              <Link
                key={cat.query}
                to={`/products?category=${cat.query}`}
                className="category-card"
              >
                <div className="category-icon-wrapper">
                  {/* Handle both string icon classes and imported image paths */}
                  {typeof cat.icon === "string" &&
                  cat.icon.startsWith("bi-") ? (
                    <i className={`bi ${cat.icon}`} />
                  ) : (
                    <img
                      src={cat.icon}
                      alt={cat.label}
                      className="category-icon-img"
                    />
                  )}
                </div>
                <span className="category-label">{cat.label}</span>
              </Link>
            ))}
          </div>
        </section>

        {/* ======== FEATURED OFFERS ======== */}
        <section className="featured">
          <div className="featured-header">
            <div>
              <h2 className="featured-title">Ofertas Destacadas</h2>
              <p className="featured-subtitle">
                Los mejores productos seleccionados para ti esta semana.
              </p>
            </div>
            <Link to="/products" className="featured-see-all">
              Ver todo <i className="bi bi-arrow-right" />
            </Link>
          </div>

          <div className="featured-grid">
            {loading ? (
              <p>Cargando ofertas...</p>
            ) : (
              featuredProducts.map((product) => (
                <ProductCard key={product.id} product={product} />
              ))
            )}
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
}
