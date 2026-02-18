import { useNavigate } from "react-router-dom";
import type { ProductResponse } from "../../../types/productTypes";
import { formatPrice } from "../../../utils/formatUtils";
import "./productCard.css";

interface ProductCardProps {
  product: ProductResponse;
}

export default function ProductCard({ product }: ProductCardProps) {
  const navigate = useNavigate();

  const calculateDiscount = () => {
    if (!product.salePrice) return 0;
    return Math.round(
      ((product.price - product.salePrice) / product.price) * 100,
    );
  };

  const hasDiscount = product.salePrice && product.salePrice < product.price;

  return (
    <div
      className="product-card"
      data-category={product.category}
      onClick={() => navigate(`/products/${product.id}`)}
      role="button"
      tabIndex={0}
      onKeyDown={(e) =>
        e.key === "Enter" && navigate(`/products/${product.id}`)
      }
    >
      <div className="product-card-image-container">
        <span className="product-badge category-badge">
          {product.category.replace(/_/g, " ")}
        </span>
        {hasDiscount && (
          <span className="product-badge discount-badge">
            -{calculateDiscount()}%
          </span>
        )}
        <img
          src={
            product.imagePath ||
            "https://placehold.co/400x300/1a1d2d/4a5fff?text=Sin+imagen"
          } /* Added a placeholder URL service */
          alt={product.name}
          className="product-image"
        />
      </div>
      <div className="product-card-info">
        <h3 className="product-title">{product.name}</h3>
        <p className="product-description">{product.description}</p>
        <div className="product-footer">
          <div className="product-price-container">
            {hasDiscount && (
              <span className="original-price">
                {formatPrice(product.price)}
              </span>
            )}
            <span className="current-price">
              {formatPrice(product.salePrice || product.price)}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
