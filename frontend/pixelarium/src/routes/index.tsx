import { createBrowserRouter } from "react-router-dom";
import Home from "../pages/home";
import Products from "../pages/products";
import ProductDetail from "../pages/productDetail";
import Cart from "../pages/cart";
import Login from "../pages/login";
import Register from "../pages/register";
import NotFoundPage from "../pages/notFound";
import ErrorPage from "../pages/error";

export const router = createBrowserRouter([
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
    path: "/product/:id",
    element: <ProductDetail />,
  },
  {
    path: "/cart",
    element: <Cart />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
  {
    path: "*",
    element: <NotFoundPage />,
  },
]);
