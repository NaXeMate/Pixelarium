import type { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext";

type Props = {
  children: ReactNode;
  requireAuth: boolean;
};

export default function ProtectedRoute({ children, requireAuth }: Props) {
  const { user, isLoading } = useAuth();

  if (isLoading) {
    return null;
  }

  if (requireAuth && !user) {
    return <Navigate to="/login" replace />;
  }

  if (!requireAuth && user) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
}
