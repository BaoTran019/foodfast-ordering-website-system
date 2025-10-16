import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import StoreContextProvider from './context/StoreContext.jsx'
import CartProvider from './context/CartContext.jsx'
import AuthProvider from './context/AuthenticationContext.jsx'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter basename="/">
      <AuthProvider>
        <StoreContextProvider>
          <CartProvider>
            <App />
          </CartProvider>
        </StoreContextProvider>
      </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
)
