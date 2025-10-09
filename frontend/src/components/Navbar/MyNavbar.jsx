import React, { useState } from 'react'
import { NavLink } from "react-router-dom";
import "./MyNavbar.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import ThemeToggle from '../ThemeToggle/ThemeToggle';
import logo from "../../assets/logo/logo.png";

// Import các modal
import LoginModal from "../../Login/LoginModal/LoginModal";
import CartAddressModal from "../CartModal/CartAddressModal";

function MyNavbar({ darkMode, setDarkMode }) {
  const [showLogin, setShowLogin] = useState(false);
  const [showCartAddress, setShowCartAddress] = useState(false);

  return (
    <>
      <Navbar
        expand='lg'
        fixed='top'
        bg={darkMode ? "dark" : "light"}
        data-bs-theme={darkMode ? "dark" : "light"}
        className='my-navbar py-3'
      >
        <Container>
          <Navbar.Brand as={NavLink} to="/">
            <img className="img-logo" src={logo} alt="logo" />
          </Navbar.Brand>
          <Navbar.Toggle aria-controls='navbarNav' />
          <Navbar.Collapse id='navbarNav'>
            <Nav className='main-menu me-auto ms-auto'>
              <Nav.Link as={NavLink} to="/" onClick={() => window.scrollTo(0, 0)}>Trang chủ</Nav.Link>
              <Nav.Link as={NavLink} to="/menu" onClick={() => window.scrollTo(0, 0)}>Thực đơn</Nav.Link>
              <Nav.Link as={NavLink} to="/" onClick={() => window.scrollTo(0, 0)}>Về chúng tôi</Nav.Link>
            </Nav>

            {/* Menu user (login, cart, theme) */}
            <Nav className='user-menu navbar-nav me-2 ms-auto'>
              {/* Login Button */}
              <Nav.Link href='#' onClick={() => setShowLogin(true)}>
                <i className="bi bi-person"></i>
              </Nav.Link>

              {/*Cart Button */}
              <Nav.Link as={NavLink} to='/cart' onClick={() => window.scrollTo(0, 0)}>
                <i className="bi bi-cart"></i>
              </Nav.Link>

              {/* Dark-mode Theme Button */}
              <Nav.Item className='d-flex align-items-center'>
                <ThemeToggle darkMode={darkMode} setDarkMode={setDarkMode} />
              </Nav.Item>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      {/* Login Modal */}
      <LoginModal show={showLogin} handleClose={() => setShowLogin(false)} />

      {/* Cart Address Modal */}
      <CartAddressModal show={showCartAddress} onClose={() => setShowCartAddress(false)} />
    </>
  )
}

export default MyNavbar;
