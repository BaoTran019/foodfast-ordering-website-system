import React, { useState } from 'react';
import { Routes, Route } from "react-router-dom";
import Home from "./Views/Home/Home";
import Menu from './Menu/Menu';
import CartPage from './Cart/CartPage';
import CheckoutPage from './Checkout/CheckoutPage';
import Footer from "./components/Footer/Footer";
import MyNavbar from "./components/Navbar/MyNavbar";
import Product_Card from './Menu/components/Product_Card/Product_Card';
import { ToastContainer } from "react-toastify";
import './App.css'


function App() {
  const [darkMode, setDarkMode] = useState(false);

  return (
    <div className='app' data-bs-theme={darkMode ? "dark" : "light"}>
      <MyNavbar darkMode={darkMode} setDarkMode={setDarkMode} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/menu" element={<Menu />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path='/checkout' element={<CheckoutPage />} />
      </Routes>
      <ToastContainer />
      <Footer />
    </div>
  );
}

export default App;
