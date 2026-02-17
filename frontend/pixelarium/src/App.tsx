import "./App.css";
import { AuthProvider } from "./context/AuthContext";
import { CartProvider } from "./context/CartContext";

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <div>
          <h1>Pixelarium</h1>
          <p>Welcome to Pixelarium</p>
        </div>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
