import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import logo from "../../assets/logo_brand_image/favicon_no_background.png";
import "./register.css";

export default function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [birthDate, setBirthDate] = useState("");
  const [birthDateError, setBirthDateError] = useState("");

  const [formData, setFormData] = useState({
    userName: "",
    email: "",
    firstName: "",
    lastName: "",
    password: "",
  });

  const [errors, setErrors] = useState<
    Partial<Record<keyof typeof formData, string>>
  >({});

  const [isLoading, setIsLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [generalError, setGeneralError] = useState("");

  const validate = (): boolean => {
    const newErrors: Partial<Record<keyof typeof formData, string>> = {};

    const today = new Date();
    const birth = new Date(birthDate);
    const age = today.getFullYear() - birth.getFullYear();
    const monthDiff = today.getMonth() - birth.getMonth();
    const realAge =
      monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())
        ? age - 1
        : age;

    if (formData.userName.trim().length < 3) {
      newErrors.userName =
        "El nombre de usuario debe tener, al menos, tres letras.";
    }

    if (!formData.email.includes("@")) {
      newErrors.email =
        "El email no es válido. Asegúrate de que el formato está bien escrito.";
    }

    let isValid = true;

    if (!birthDate) {
      setBirthDateError("La fecha de nacimiento es obligatoria.");
      isValid = false;
    } else if (realAge < 18) {
      setBirthDateError("Debes ser mayor de 18 años para registrarte.");
      isValid = false;
    } else {
      setBirthDateError("");
    }

    if (formData.firstName.trim().length === 0) {
      newErrors.firstName = "El nombre es obligatorio.";
    } else if (formData.firstName.trim().length < 3) {
      newErrors.firstName = "El nombre debe tener, al menos, tres letras.";
    }

    if (formData.lastName.trim().length === 0) {
      newErrors.lastName = "El apellido es obligatorio.";
    } else if (formData.lastName.trim().length < 3) {
      newErrors.lastName = "El apellido debe tener, al menos, tres letras.";
    }

    if (formData.password.length < 8) {
      newErrors.password =
        "La contraseña debe tener, al menos, ocho caracteres.";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const getPasswordStrength = (password: string) => {
    if (password.length === 0) return 0;
    if (password.length < 6) return 1; // Weak
    if (password.length < 10) return 2; // Medium
    if (password.length >= 10) return 3; // Strong
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); // Prevent form reload
    setGeneralError(""); // Clear previous errors

    if (!validate()) return; // For if there are validation errors

    try {
      setIsLoading(true);
      await register(formData); // Call AuthContext
      navigate("/"); // Redirect to home after registration
    } catch (error) {
      setGeneralError(
        "No se ha podido completar el registro. Inténtalo de nuevo.",
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="register-page">
      <div className="register-card">
        {/* Header */}
        <div className="register-header">
          <img src={logo} alt="Logo" className="register-logo" />
          <h1>Pixelarium</h1>
          <p>Únete a la mejor comunidad gamer</p>
        </div>

        {/* General error */}
        {generalError && <div className="general-error">{generalError}</div>}

        <form onSubmit={handleSubmit} noValidate>
          {/* Row 1: userName + email */}
          <div className="form-row">
            {/* Username field */}
            <div className="form-group">
              <label htmlFor="userName">Nombre de usuario</label>
              <input
                id="userName"
                name="userName"
                type="text"
                value={formData.userName}
                onChange={handleChange}
                placeholder="gamer123"
                disabled={isLoading}
              />
              {errors.userName && (
                <span className="field-error">{errors.userName}</span>
              )}
            </div>

            <div className="form-group">
              {/* Email field */}
              <label htmlFor="email">Email</label>
              <input
                id="email"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="example@pixelarium.com"
                disabled={isLoading}
              />
              {errors.email && (
                <span className="field-error">{errors.email}</span>
              )}
            </div>
          </div>

          {/* Row 2: firstName + lastName */}
          <div className="form-row">
            {/* First name field */}
            <div className="form-group">
              <label htmlFor="firstName">Nombre</label>
              <input
                id="firstName"
                name="firstName"
                type="text"
                value={formData.firstName}
                onChange={handleChange}
                placeholder="John"
                disabled={isLoading}
              />
              {errors.firstName && (
                <span className="field-error">{errors.firstName}</span>
              )}
            </div>

            <div className="form-group">
              {/* Last name field */}
              <label htmlFor="lastName">Apellido</label>
              <input
                id="lastName"
                name="lastName"
                type="text"
                value={formData.lastName}
                onChange={handleChange}
                placeholder="Doe"
                disabled={isLoading}
              />
              {errors.lastName && (
                <span className="field-error">{errors.lastName}</span>
              )}
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="birthDate">Fecha de nacimiento</label>
            <input
              id="birthDate"
              name="birthDate"
              type="date"
              value={birthDate}
              onChange={(e) => setBirthDate(e.target.value)}
              max={new Date().toISOString().split("T")[0]}
              disabled={isLoading}
            />
            {birthDateError && (
              <span className="field-error">{birthDateError}</span>
            )}
          </div>

          {/* Password field */}
          <div className="form-group">
            <label htmlFor="password">Contraseña</label>
            <div className="password-wrapper">
              <input
                id="password"
                name="password"
                type={showPassword ? "text" : "password"}
                value={formData.password}
                onChange={handleChange}
                placeholder="••••••••"
                disabled={isLoading}
              />
              <button
                type="button"
                className="toggle-password"
                onClick={() => setShowPassword((prev) => !prev)}
                aria-label={
                  showPassword ? "Ocultar contraseña" : "Mostrar contraseña"
                }
              >
                <i
                  className={`bi ${showPassword ? "bi-eye-slash" : "bi-eye"}`}
                />
              </button>
            </div>
            {errors.password && (
              <span className="field-error">{errors.password}</span>
            )}

            {/* Security Bar */}
            {formData.password.length > 0 && (
              <div className="password-strength">
                <div className="strength-bars">
                  {[1, 2, 3].map((level) => (
                    <div
                      key={level}
                      className={`strength-bar ${
                        (getPasswordStrength(formData.password) ?? 0) >= level
                          ? `strength-${getPasswordStrength(formData.password)}`
                          : ""
                      }`}
                    />
                  ))}
                </div>
                <span className="strength-label">
                  {
                    ["", "Débil", "Media", "Fuerte"][
                      getPasswordStrength(formData.password) ?? 0
                    ]
                  }
                </span>
              </div>
            )}
          </div>

          {/* Submit button */}
          <button type="submit" className="submit-button" disabled={isLoading}>
            {isLoading ? "Registrando..." : "Registrarse"}
          </button>

          {/* Link to login */}
          <p className="login-link">
            ¿Ya tienes cuenta? <a href="/login">Inicia sesión</a>
          </p>
        </form>
      </div>
    </div>
  );
}
