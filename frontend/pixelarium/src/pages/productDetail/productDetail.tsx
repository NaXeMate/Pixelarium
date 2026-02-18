import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Header from "../../components/layout/header/header";
import Footer from "../../components/layout/footer/footer";
import { getProductById } from "../../services/productService";
import { useCart } from "../../context/CartContext";
import { useAuth } from "../../context/AuthContext";
import type { ProductResponse } from "../../types";
import "./productDetail.css";

export default function ProductDetail() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { addToCart, items } = useCart();
  const { user } = useAuth();

  const [product, setProduct] = useState<ProductResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchProduct() {
      if (!id) return;

      try {
        setLoading(true);
        const data = await getProductById(Number(id));
        setProduct(data);
      } catch (err) {
        console.error(err);
        setError("Producto no encontrado o error al cargar.");
      } finally {
        setLoading(false);
      }
    }

    fetchProduct();
  }, [id]);

  const cartItem = items.find((item) => item.product.id === product?.id);
  const currentQtyInCart = cartItem ? cartItem.quantity : 0;
  const isStockLimitReached = product
    ? currentQtyInCart >= product.stock
    : false;

  const handleAddToCart = () => {
    if (product && product.stock > 0 && !isStockLimitReached) {
      addToCart(product, 1);
    }
  };

  const getCategoryLabel = (cat: string) => {
    // Simple mapping or return capitalized
    // Based on Products.tsx labels, we could reuse them but for now direct return is fine
    // Or we can map them if we had the map handy.
    /* 
      const categories = [
        { id: "ALL", label: "Todos" },
        { id: "NINTENDO_SWITCH", label: "Videojuegos" },
        { id: "NINTENDO_SWITCH_2", label: "Consolas" },
        { id: "PC", label: "PC Gaming" },
        { id: "ACCESSORIES", label: "Periféricos" },
        { id: "APPLE", label: "Apple" },
      ];
      */
    switch (cat) {
      case "NINTENDO_SWITCH":
        return "Videojuegos";
      case "NINTENDO_SWITCH_2":
        return "Consolas";
      case "PC":
        return "PC Gaming";
      case "ACCESSORIES":
        return "Periféricos";
      case "APPLE":
        return "Apple";
      default:
        return cat;
    }
  };

  if (loading) {
    return (
      <div className="product-detail-page">
        <Header />
        <div className="product-detail-container">
          <p>Cargando producto...</p>
        </div>
        <Footer />
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="product-detail-page">
        <Header />
        <div className="product-detail-container">
          <div className="error-state">
            <h2>Error</h2>
            <p>{error || "Producto no encontrado"}</p>
            <button className="back-btn" onClick={() => navigate("/products")}>
              Volver al catálogo
            </button>
          </div>
        </div>
        <Footer />
      </div>
    );
  }

  // Calculate discount percentage if sale price exists
  const discountPercentage = product.salePrice
    ? Math.round(((product.price - product.salePrice) / product.price) * 100)
    : 0;

  return (
    <div className="product-detail-page">
      <Header />

      <main className="product-detail-container">
        <div className="product-main-content">
          {/* Left Column: Image */}
          <div className="product-image-section">
            <img
              src={product.imagePath || "/placeholder.png"}
              alt={product.name}
              className="product-image"
            />
          </div>

          {/* Right Column: Info */}
          <div className="product-info-section">
            <span className="product-category-tag">
              {getCategoryLabel(product.category)}
            </span>

            <h1 className="product-title">{product.name}</h1>

            <div className="product-price-container">
              {product.salePrice ? (
                <>
                  <span className="current-price">${product.salePrice}</span>
                  <span className="original-price">${product.price}</span>
                  <span className="discount-badge">
                    Save {discountPercentage}%
                  </span>
                </>
              ) : (
                <span className="current-price">${product.price}</span>
              )}
            </div>

            <div
              className={`stock-status ${product.stock > 0 ? "in-stock" : "out-of-stock"}`}
            >
              <span className="stock-indicator"></span>
              {product.stock > 0
                ? `En stock (${product.stock} disponibles)`
                : "Agotado"}
            </div>

            <p className="product-description">{product.description}</p>

            <div className="add-to-cart-container">
              {!user ? (
                <button
                  className="add-to-cart-btn login-required-btn"
                  onClick={() => navigate("/login")}
                >
                  Iniciar sesión
                </button>
              ) : (
                <button
                  className="add-to-cart-btn"
                  onClick={handleAddToCart}
                  disabled={product.stock <= 0 || isStockLimitReached}
                >
                  {product.stock <= 0 ? (
                    "Agotado"
                  ) : isStockLimitReached ? (
                    "Límite de stock alcanzado"
                  ) : (
                    <>
                      <svg
                        width="20"
                        height="20"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                      >
                        <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path>
                        <line x1="3" y1="6" x2="21" y2="6"></line>
                        <path d="M16 10a4 4 0 0 1-8 0"></path>
                      </svg>
                      Añadir al carrito
                    </>
                  )}
                </button>
              )}
            </div>

            {/* Technical Details */}
            <div className="technical-details-section">
              <div className="technical-details-title">Detalles Técnicos</div>
              <div className="technical-details-grid">
                <div className="detail-item">
                  <span className="detail-label">ID Producto</span>
                  <span className="detail-value">{product.id}</span>
                </div>
                <div className="detail-item">
                  <span className="detail-label">Categoría</span>
                  <span className="detail-value">
                    {getCategoryLabel(product.category)}
                  </span>
                </div>
                {/* Removed Platform/Developer as requested/simplified */}
              </div>
            </div>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  );
}
