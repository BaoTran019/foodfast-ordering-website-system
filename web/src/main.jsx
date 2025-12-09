import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import StoreContextProvider from './context/StoreContext.jsx'
import CartProvider from './context/CartContext.jsx'
import AuthProvider from './context/AuthenticationContext.jsx'
import UserProvider from './context/UserContext.jsx'
import OrderProvider from './context/OrderContext.jsx'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter basename="/">
      <AuthProvider>
        <UserProvider>
          <StoreContextProvider>
            <CartProvider>
              <OrderProvider>
                <App />
              </OrderProvider>
            </CartProvider>
          </StoreContextProvider>
        </UserProvider>
      </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
)
