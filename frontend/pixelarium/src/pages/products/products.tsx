import "./products.css";
import Header from "../../components/layout/header/header";
import Footer from "../../components/layout/footer/footer";
import ProductCard from "../../components/layout/productCard/productCard";
import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import type { ProductResponse, Category } from "../../types";
import {
  getAllProducts,
  getProductsByPriceRange,
  searchProducts,
} from "../../services/productService";

export default function Products() {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [displayProducts, setDisplayProducts] = useState<ProductResponse[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<Category | "ALL">(
    "ALL",
  );
  const [minPrice, setMinPrice] = useState<string>("");
  const [maxPrice, setMaxPrice] = useState<string>("");
  const [onlyOnSale, setOnlyOnSale] = useState(false);
  const [sortOrder, setSortOrder] = useState<
    "PRICE_ASC" | "PRICE_DESC" | "NAME_ASC"
  >("PRICE_ASC");
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  // Read search query from URL params (set by the Header search bar)
  const [searchParams] = useSearchParams();

  const ITEMS_PER_PAGE = 8;

  const categories = [
    { id: "ALL", label: "Todos" },
    { id: "NINTENDO_SWITCH", label: "Videojuegos" },
    { id: "NINTENDO_SWITCH_2", label: "Consolas" },
    { id: "PC", label: "PC Gaming" },
    { id: "ACCESSORIES", label: "Periféricos" },
    { id: "APPLE", label: "Apple" },
  ] as const;

  // Fetches all products or filtered by price range
  const loadAllProducts = useCallback(async () => {
    try {
      setIsLoading(true);
      setError("");

      let data: ProductResponse[] = [];

      if (minPrice && maxPrice) {
        data = await getProductsByPriceRange(
          Number(minPrice),
          Number(maxPrice),
        );
      } else {
        data = await getAllProducts();
      }

      setProducts(data);
    } catch (err) {
      setError("Error al cargar los productos");
    } finally {
      setIsLoading(false);
    }
  }, [minPrice, maxPrice]);

  // On mount: if there's a search param in the URL, run a search;
  // otherwise load all products normally
  useEffect(() => {
    const queryFromUrl = searchParams.get("search");

    if (queryFromUrl) {
      const runSearch = async () => {
        try {
          setIsLoading(true);
          setError("");
          const data = await searchProducts(queryFromUrl);
          setProducts(data);
        } catch (err) {
          setError("Error al buscar productos");
        } finally {
          setIsLoading(false);
        }
      };
      runSearch();
    } else {
      loadAllProducts();
    }
  }, [loadAllProducts, searchParams]);

  // Client-side filtering and sorting over the fetched products
  useEffect(() => {
    let result = [...products];

    // Filter by category
    if (selectedCategory !== "ALL") {
      result = result.filter((p) => p.category === selectedCategory);
    }

    // Filter by sale price
    if (onlyOnSale) {
      result = result.filter((p) => p.salePrice && p.salePrice < p.price);
    }

    // Sort results
    result.sort((a, b) => {
      const priceA = a.salePrice || a.price;
      const priceB = b.salePrice || b.price;

      switch (sortOrder) {
        case "PRICE_ASC":
          return priceA - priceB;
        case "PRICE_DESC":
          return priceB - priceA;
        case "NAME_ASC":
          return a.name.localeCompare(b.name);
        default:
          return 0;
      }
    });

    setDisplayProducts(result);
    setCurrentPage(1); // Reset to page 1 whenever filters change
  }, [products, selectedCategory, onlyOnSale, sortOrder]);

  // Clears all active filters and reloads full product list
  const handleClearFilters = () => {
    setMinPrice("");
    setMaxPrice("");
    setSelectedCategory("ALL");
    setOnlyOnSale(false);
    setSortOrder("PRICE_ASC");
  };

  // Pagination
  const totalPages = Math.ceil(displayProducts.length / ITEMS_PER_PAGE);
  const paginatedProducts = displayProducts.slice(
    (currentPage - 1) * ITEMS_PER_PAGE,
    currentPage * ITEMS_PER_PAGE,
  );

  return (
    <div className="products-page">
      <Header />

      <div className="products-layout-container">
        {/* Sidebar */}
        <aside className="products-sidebar">
          {/* Category filter */}
          <div className="sidebar-section">
            <h3 className="sidebar-title">Categorías</h3>
            <div className="category-list">
              {categories.map((cat) => (
                <div
                  key={cat.id}
                  className={`category-item ${selectedCategory === cat.id ? "active" : ""}`}
                  onClick={() =>
                    setSelectedCategory(cat.id as Category | "ALL")
                  }
                >
                  <span className="checkbox-mock"></span>
                  <span className="category-label">{cat.label}</span>
                </div>
              ))}
            </div>
          </div>

          {/* Price range filter */}
          <div className="sidebar-section">
            <h3 className="sidebar-title">Precio (€)</h3>
            <div className="price-inputs">
              <input
                type="number"
                placeholder="Min"
                value={minPrice}
                onChange={(e) => setMinPrice(e.target.value)}
              />
              <input
                type="number"
                placeholder="Max"
                value={maxPrice}
                onChange={(e) => setMaxPrice(e.target.value)}
              />
            </div>
            <div className="price-filter-actions">
              <button className="apply-filter-btn" onClick={loadAllProducts}>
                Aplicar
              </button>
              <button className="clear-filter-btn" onClick={handleClearFilters}>
                Limpiar
              </button>
            </div>
          </div>

          {/* Sale toggle */}
          <div className="sidebar-section toggle-section">
            <span className="sidebar-label">Solo en oferta</span>
            <label className="switch">
              <input
                type="checkbox"
                checked={onlyOnSale}
                onChange={(e) => setOnlyOnSale(e.target.checked)}
              />
              <span className="slider round"></span>
            </label>
          </div>
        </aside>

        {/* Main content */}
        <main className="products-main-content">
          {/* Title, search context indicator and sort controls */}
          <div className="products-header-bar">
            <div>
              <h1 className="products-main-title">
                {searchParams.get("search")
                  ? `Resultados para "${searchParams.get("search")}"`
                  : "Catálogo"}
              </h1>
              <p className="products-subtitle">
                Explora nuestra colección de tecnología y entretenimiento.
              </p>
            </div>

            <div className="products-sort-controls">
              <span className="sort-label">Ordenar por:</span>
              <select
                value={sortOrder}
                onChange={(e) =>
                  setSortOrder(
                    e.target.value as "PRICE_ASC" | "PRICE_DESC" | "NAME_ASC",
                  )
                }
                className="sort-select"
              >
                <option value="PRICE_ASC">Precio: Menor a Mayor</option>
                <option value="PRICE_DESC">Precio: Mayor a Menor</option>
                <option value="NAME_ASC">Nombre: A-Z</option>
              </select>
            </div>
          </div>

          <div className="results-count">
            Mostrando {displayProducts.length} resultados
          </div>

          {/* Loading state */}
          {isLoading && <p className="status-message">Cargando productos...</p>}

          {/* Error state */}
          {!isLoading && error && (
            <p className="status-message error">{error}</p>
          )}

          {/* Products grid and pagination */}
          {!isLoading && !error && displayProducts.length > 0 && (
            <>
              <div className="products-grid">
                {paginatedProducts.map((product) => (
                  <ProductCard key={product.id} product={product} />
                ))}
              </div>

              {totalPages > 1 && (
                <div className="pagination">
                  <button
                    disabled={currentPage === 1}
                    onClick={() => setCurrentPage((p) => p - 1)}
                    className="page-btn arrow"
                  >
                    ‹
                  </button>
                  {Array.from({ length: totalPages }, (_, i) => i + 1).map(
                    (page) => (
                      <button
                        key={page}
                        className={`page-btn ${currentPage === page ? "active" : ""}`}
                        onClick={() => setCurrentPage(page)}
                      >
                        {page}
                      </button>
                    ),
                  )}
                  <button
                    disabled={currentPage === totalPages}
                    onClick={() => setCurrentPage((p) => p + 1)}
                    className="page-btn arrow"
                  >
                    ›
                  </button>
                </div>
              )}
            </>
          )}

          {/* Empty state */}
          {!isLoading && !error && displayProducts.length === 0 && (
            <div className="empty-state">
              <p>No se encontraron productos con los filtros seleccionados.</p>
              <button onClick={handleClearFilters}>Limpiar filtros</button>
            </div>
          )}
        </main>
      </div>

      <Footer />
    </div>
  );
}
