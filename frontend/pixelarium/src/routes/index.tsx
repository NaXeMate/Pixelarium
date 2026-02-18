import { createBrowserRouter } from "react-router-dom";
import Home from "../pages/home";
import Products from "../pages/products";
import ProductDetail from "../pages/productDetail";
import Cart from "../pages/cart";
import Login from "../pages/login";
import Register from "../pages/register";
import NotFoundPage from "../pages/notFound";
import ErrorPage from "../pages/error";
import ProtectedRoute from "../components/common/protectedRoute";

export const router = createBrowserRouter([
  // Public routes
  {
    path: "/",
    element: <Home />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/products",
    element: <Products />,
  },
  {
    path: "/products/:id",
    element: <ProductDetail />,
  },

  // Unlogued users routes
  {
    path: "/login",
    element: (
      <ProtectedRoute requireAuth={false}>
        <Login />
      </ProtectedRoute>
    ),
  },
  {
    path: "/register",
    element: (
      <ProtectedRoute requireAuth={false}>
        <Register />
      </ProtectedRoute>
    ),
  },

  // Logued users routes
  {
    path: "/cart",
    element: (
      <ProtectedRoute requireAuth={true}>
        <Cart />
      </ProtectedRoute>
    ),
  },

  // Not found route
  {
    path: "*",
    element: <NotFoundPage />,
  },
]);
