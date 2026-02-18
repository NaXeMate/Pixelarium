import { Link, useNavigate } from "react-router-dom";
import { useCart } from "../../context/CartContext";
import Header from "../../components/layout/header/header";
import Footer from "../../components/layout/footer/footer";
import "./cart.css";
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Cart() {
  const {
    items,
    removeFromCart,
    updateQuantity,
    getTotalPrice,
    getTotalItems,
  } = useCart();
  const navigate = useNavigate();

  const handleQuantityChange = (
    productId: number,
    newQuantity: number,
    startStock: number,
  ) => {
    if (newQuantity >= 1 && newQuantity <= startStock) {
      updateQuantity(productId, newQuantity);
    }
  };

  const subtotal = getTotalPrice();
  const shipping = 0; // Free shipping
  const total = subtotal + shipping;

  return (
    <div className="cart-page">
      <Header />
      <main className="cart-content">
        <div className="cart-container">
          <div className="cart-header">
            <h1 className="cart-title">TU CARRITO</h1>
            <span className="cart-count">
              {getTotalItems()} artículo{getTotalItems() !== 1 ? "s" : ""}
            </span>
          </div>

          {items.length === 0 ? (
            <div className="empty-cart">
              <p>Tu carrito está vacío.</p>
              <Link to="/products" className="continue-shopping">
                <i className="bi bi-arrow-left"></i> Seguir comprando
              </Link>
            </div>
          ) : (
            <div className="cart-layout">
              <div className="cart-items-section">
                <div className="cart-items-header">
                  <span>PRODUCTO</span>
                  <div className="header-right">
                    <span>CANTIDAD</span>
                    <span>PRECIO UNITARIO</span>
                    <span>TOTAL</span>
                  </div>
                </div>

                <div className="cart-items-list">
                  {items.map((item) => (
                    <div key={item.product.id} className="cart-item">
                      <div className="item-info">
                        <img
                          src={item.product.imagePath}
                          alt={item.product.name}
                          className="item-image"
                        />
                        <div className="item-details">
                          <h3 className="item-name">{item.product.name}</h3>
                          <p className="item-platform">
                            {item.product.category}
                          </p>
                          <span className="stock-status">Disponible</span>
                        </div>
                      </div>

                      <div className="item-controls">
                        <div className="quantity-control">
                          <button
                            onClick={() =>
                              handleQuantityChange(
                                item.product.id,
                                item.quantity - 1,
                                item.product.stock,
                              )
                            }
                            disabled={item.quantity <= 1}
                            className="qty-btn"
                          >
                            <i className="bi bi-dash"></i>
                          </button>
                          <span className="qty-value">{item.quantity}</span>
                          <button
                            onClick={() =>
                              handleQuantityChange(
                                item.product.id,
                                item.quantity + 1,
                                item.product.stock,
                              )
                            }
                            disabled={item.quantity >= item.product.stock}
                            className="qty-btn"
                          >
                            <i className="bi bi-plus"></i>
                          </button>
                        </div>

                        <div className="price-info">
                          <span className="unit-price">
                            {item.product.salePrice
                              ? item.product.salePrice.toFixed(2)
                              : item.product.price.toFixed(2)}
                            €
                          </span>
                          <span className="item-total-price">
                            {(
                              (item.product.salePrice || item.product.price) *
                              item.quantity
                            ).toFixed(2)}
                            €
                          </span>
                        </div>

                        <button
                          className="delete-btn"
                          onClick={() => removeFromCart(item.product.id)}
                          aria-label="Eliminar producto"
                        >
                          <i className="bi bi-trash"></i>
                        </button>
                      </div>
                    </div>
                  ))}
                </div>

                <Link to="/products" className="continue-shopping-link">
                  <i className="bi bi-arrow-left"></i> Seguir comprando
                </Link>
              </div>

              <aside className="cart-summary">
                <h2 className="summary-title">RESUMEN DEL PEDIDO</h2>

                <div className="summary-row">
                  <span>Subtotal</span>
                  <span>{subtotal.toFixed(2).replace(".", ",")}€</span>
                </div>

                <div className="summary-row discount">
                  {/* Discounts not implemented yet as per instructions */}
                  {/* <span>Descuentos</span>
                  <span>-0,00€</span> */}
                </div>

                <div className="summary-row">
                  <span>Envío</span>
                  <span className="free-shipping">GRATIS</span>
                </div>

                <div className="summary-divider"></div>

                <div className="summary-row total">
                  <span>Total</span>
                  <div className="total-value-container">
                    <span className="total-amount">
                      {total.toFixed(2).replace(".", ",")}€
                    </span>
                    <span className="vat-included">IVA incluido</span>
                  </div>
                </div>

                <button
                  className="checkout-btn"
                  onClick={() => navigate("/checkout")}
                >
                  Finalizar compra <i className="bi bi-arrow-right"></i>
                </button>

                <div className="secure-payment">
                  <i className="bi bi-lock-fill"></i> Pago 100% Seguro
                </div>
              </aside>
            </div>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
}
