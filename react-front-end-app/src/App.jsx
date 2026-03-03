import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import NavBar from "./components/NavBar.jsx";
import Home from "./pages/Home";
import About from "./pages/About";
import Footer from "./components/Footer.jsx";

function App() {
  return (
    <>
      <NavBar />
      <main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}

export default App;
