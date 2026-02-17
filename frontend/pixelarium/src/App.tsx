import "./App.css";
import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <AuthProvider>
      <div>
        <h1>Pixelarium</h1>
        <p>Welcome to Pixelarium</p>
      </div>
    </AuthProvider>
  );
}

export default App;
