import "./home.css";
import Header from "../../components/layout/header/header";
import Footer from "../../components/layout/footer/footer";

export default function home() {
  return (
    <div className="home-page">
      <Header />
      <main className="home-content">
        <h1>Home</h1>
        <p>Página principal - En construcción</p>
      </main>
      <Footer />
    </div>
  );
}
