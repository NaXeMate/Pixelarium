/**
 * @file CartContext.tsx
 * @description Provides the shopping cart context and management logic,
 * including adding/removing items, quantity updates, and persistence in localStorage.
 */

import {
  createContext,
  useContext,
  useEffect,
  useState,
  type ReactNode,
} from "react";
import type { CartItem, ProductResponse } from "../types";

/**
 * Interface defining the methods and state available in the shopping cart context.
 */

type CartContextType = {
  items: CartItem[];
  addToCart: (product: ProductResponse, quantity: number) => void;
  removeFromCart: (productId: number) => void;
  updateQuantity: (productId: number, quantity: number) => void;
  clearCart: () => void;
  getTotalPrice: () => number;
  getTotalItems: () => number;
};

/**
 * The Shopping Cart Context object.
 */
const CartContext = createContext<CartContextType | undefined>(undefined);

type CartProviderProps = {
  children: ReactNode;
};

/**
 * Provider component that manages the shopping cart state and provides it to its children.
 */
export function CartProvider({ children }: CartProviderProps) {
  const [items, setItems] = useState<CartItem[]>([]);

  /**
   * Adds a product to the cart. If the product already exists, it updates the quantity.
   */
  const addToCart = (product: ProductResponse, quantity: number = 1) => {
    setItems((currentItems) => {
      const existingItem = currentItems.find(
        (item) => item.product.id === product.id,
      );

      if (existingItem) {
        return currentItems.map((item) =>
          item.product.id === product.id
            ? { ...item, quantity: item.quantity + quantity }
            : item,
        );
      }

      return [...currentItems, { product, quantity }];
    });
  };

  /**
   * Removes a product from the cart based on its ID.
   */
  const removeFromCart = (productId: number) => {
    setItems((currentItems) =>
      currentItems.filter((item) => item.product.id !== productId),
    );
  };

  /**
   * Updates the quantity of a specific item in the cart.
   */
  const updateQuantity = (productId: number, quantity: number) => {
    setItems((currentItems) =>
      currentItems.map((item) =>
        item.product.id === productId ? { ...item, quantity } : item,
      ),
    );
  };

  /**
   * Removes all items from the shopping cart.
   */
  const clearCart = () => {
    setItems([]);
  };

  /**
   * Calculates the total price of all items in the cart.
   */
  const getTotalPrice = () => {
    return items.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0,
    );
  };

  /**
   * Returns the total number of items present in the cart.
   */
  const getTotalItems = () => {
    return items.reduce((total, item) => total + item.quantity, 0);
  };

  /**
   * Effect to load the shopping cart from localStorage when the provider mounts.
   */
  useEffect(() => {
    try {
      const savedCart = localStorage.getItem("cart");
      if (savedCart) {
        setItems(JSON.parse(savedCart));
      }
    } catch (error) {
      console.error("Failed to load cart:", error);
      localStorage.removeItem("cart");
    }
  }, []);

  /**
   * Effect to persist the cart state to localStorage whenever it changes.
   */
  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(items));
  }, [items]);

  return (
    <CartContext.Provider
      value={{
        items,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        getTotalPrice,
        getTotalItems,
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

/**
 * Custom hook to access the shopping cart context.
 * @throws {Error} If used outside of a CartProvider.
 */
export function useCart() {
  const context = useContext(CartContext);

  if (context === undefined) {
    throw new Error("useCart must be used within a CartProvider");
  }

  return context;
}
