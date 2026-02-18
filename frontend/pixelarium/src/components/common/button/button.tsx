import { Link } from "react-router-dom";
import "./button.css";

interface ButtonProps {
  to: string;
  variant?: "primary" | "secondary";
  icon?: string;
  children: React.ReactNode;
}

export default function Button({
  to,
  variant = "primary",
  icon,
  children,
}: ButtonProps) {
  return (
    <Link to={to} className={`btn btn--${variant}`}>
      {icon && <i className={`bi ${icon}`}></i>}
      {children}
    </Link>
  );
}
