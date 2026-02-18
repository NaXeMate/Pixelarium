/**
 * @file AuthContext.tsx
 * @description Provides authentication context and logic for the application,
 * including user state management, login, registration, and session persistence.
 */

import {
  createContext,
  useContext,
  useState,
  useEffect,
  type ReactNode,
} from "react";
import type { UserResponse, CreateUser } from "../types";
import { login as loginService, createUser } from "../services/userService";

/**
 * Interface defining the shape of the authentication context.
 */

type AuthContextType = {
  user: UserResponse | null;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (userData: CreateUser) => Promise<void>;
  logout: () => void;
};

/**
 * The Authentication Context object.
 */
const AuthContext = createContext<AuthContextType | undefined>(undefined);

type AuthProviderProps = {
  children: ReactNode;
};

/**
 * Provider component that wraps the application and manages the authentication state.
 */
export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<UserResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  /**
   * Performs the login operation using the userService and persists the user session.
   */
  const login = async (email: string, password: string) => {
    try {
      setIsLoading(true);
      const userData = await loginService(email, password);
      setUser(userData);
      localStorage.setItem("user", JSON.stringify(userData));
    } catch (error) {
      console.error("Login failed:", error);
      throw error;
    } finally {
      setIsLoading(false);
    }
  };

  /**
   * Registers a new user and automatically logs them in.
   */
  const register = async (userData: CreateUser) => {
    try {
      setIsLoading(true);
      const newUser = await createUser(userData);
      setUser(newUser);
      localStorage.setItem("user", JSON.stringify(newUser));
    } catch (error) {
      console.error("Register failed:", error);
      throw error;
    } finally {
      setIsLoading(false);
    }
  };

  /**
   * Clears the user session and removes data from localStorage.
   */
  const logout = () => {
    setUser(null);
    localStorage.removeItem("user");
  };

  /**
   * Effect hook to load the persisted user session from localStorage on application start.
   */
  useEffect(() => {
    const loadUser = () => {
      try {
        const savedUser = localStorage.getItem("user");
        if (savedUser) {
          setUser(JSON.parse(savedUser));
        }
      } catch (error) {
        console.error("Failed to load user:", error);
        localStorage.removeItem("user");
      } finally {
        setIsLoading(false);
      }
    };

    loadUser();
  }, []);

  return (
    <AuthContext.Provider value={{ user, isLoading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

/**
 * Custom hook to access the authentication context.
 * @throws {Error} If used outside of an AuthProvider.
 */
export function useAuth() {
  const context = useContext(AuthContext);

  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }

  return context;
}
