import "./products.css";
import Header from "../../components/layout/header/header";
import Footer from "../../components/layout/footer/footer";
import { useCallback, useEffect, useState } from "react";
import type { ProductResponse, Category } from "../../types";
import {
  getAllProducts,
  getProductsByPriceRange,
} from "../../services/productService";
import ProductCard from "../../components/layout/productCard";
import { Link } from "react-router-dom";

export default function Products() {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [displayProducts, setDisplayProducts] = useState<ProductResponse[]>([]);
  // search removed as per mockup
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

  const ITEMS_PER_PAGE = 8;

  const categories = [
    { id: "ALL", label: "Todos", count: 0 }, // Count logic would need backend support or full fetch
    { id: "NINTENDO_SWITCH", label: "Videojuegos", count: 128 }, // Mock counts
    { id: "NINTENDO_SWITCH_2", label: "Consolas", count: 45 },
    { id: "PC", label: "PC Gaming", count: 86 },
    { id: "ACCESSORIES", label: "Periféricos", count: 32 },
    { id: "APPLE", label: "Apple", count: 14 },
  ] as const;

  const loadAllProducts = useCallback(async () => {
    try {
      setIsLoading(true);
      setError("");
      // Ideally this would be a filtered fetch, but for now we fetch all and filter client-side
      // EXCEPT for price range if specified and no other complex filters overlap unfavorably.
      // However, to keep it simple and consistent with current backend capabilities shown:
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

  useEffect(() => {
    loadAllProducts();
  }, [loadAllProducts]);

  // Client-side Filtering & Sorting Effect
  useEffect(() => {
    let result = [...products];

    // 1. Search removed
    // if (searchQuery.trim()) {
    //   const query = searchQuery.toLowerCase();
    //   result = result.filter(
    //     (p) =>
    //       p.name.toLowerCase().includes(query) ||
    //       p.description.toLowerCase().includes(query),
    //   );
    // }

    // 2. Category
    if (selectedCategory !== "ALL") {
      result = result.filter((p) => p.category === selectedCategory);
    }

    // 3. Sale
    if (onlyOnSale) {
      result = result.filter((p) => p.salePrice && p.salePrice < p.price);
    }

    // 4. Sorting
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
    setCurrentPage(1); // Reset to page 1 on filter change
  }, [products, selectedCategory, onlyOnSale, sortOrder]);

  // Pagination Logic
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
                  {/* <span className="category-count">{cat.count}</span> */}
                </div>
              ))}
            </div>
          </div>

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
              <button
                className="clear-filter-btn"
                onClick={() => {
                  setMinPrice("");
                  setMaxPrice("");
                  // Optionally trigger a reload of all products immediately
                  // loadAllProducts();
                  // But the useEffect with loadAllProducts depends on minPrice/maxPrice change?
                  // No, loadAllProducts is only triggered on effect mount or when loadAllProducts is called manually.
                  // Actually, loadAllProducts is in the dependency array of useEffect.
                  // So clearing them will trigger the effect if loadAllProducts is recalculated.
                  // Wait, loadAllProducts depends on minPrice and maxPrice because of the useCallback dependencies.
                  // So changing minPrice/maxPrice will recreate loadAllProducts, which triggers the effect.
                }}
              >
                Limpiar
              </button>
            </div>
          </div>

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

        {/* Main Content */}
        <main className="products-main-content">
          <div className="products-breadcrumb">
            <Link to="/">Inicio</Link>
            <span className="separator">›</span>
            <span className="current">Productos</span>
          </div>

          <div className="products-header-bar">
            <div>
              <h1 className="products-main-title">Catálogo</h1>
              <p className="products-subtitle">
                Explora nuestra colección de tecnología y entretenimiento.
              </p>
            </div>

            <div className="products-sort-controls">
              <span className="sort-label">Ordenar por:</span>
              <select
                value={sortOrder}
                onChange={(e) => setSortOrder(e.target.value as any)}
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

          {isLoading && <p className="status-message">Cargando productos...</p>}
          {!isLoading && error && (
            <p className="status-message error">{error}</p>
          )}

          {!isLoading && !error && (
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
        </main>
      </div>

      <Footer />
    </div>
  );
}
