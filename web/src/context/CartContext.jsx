import { createContext, useContext, useState, useEffect } from "react";
import { toast } from "react-toastify";
import { AuthContext } from "./AuthenticationContext";
import {
  getCart,
  addToCartApi,
  updateQuantityApi,
  removeItemApi,
  clearCartApi
} from "../api/cartAPI";

export const CartContext = createContext();

function CartProvider({ children }) {
  const [cart, setCart] = useState({ cartItems: [], totalPrice: 0 });
  const [loading, setLoading] = useState(true);
  const { auth } = useContext(AuthContext);
  const userId = auth.userId;

  useEffect(() => {
    const fetchCartData = async () => {
      if (!userId) return setLoading(false);
      try {
        const data = await getCart(userId);
        setCart(data || { cartItems: [], totalPrice: 0 });
      } catch (err) {
        console.error("❌ Lỗi load cart:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchCartData();
  }, [userId]);

  const addToCart = async (food, quantity) => {
    try {
      const updatedCart = await addToCartApi(userId, food.id, quantity);
      setCart(updatedCart);
    } catch (err) {
      console.error(err);
      throw err;
    }
  };

  const updateQuantity = async (productId, delta) => {
    try {
      const updated = await updateQuantityApi(userId, productId, delta);
      setCart(updated || { cartItems: [], totalPrice: 0 });
    } catch (err) {
      console.error("❌ Lỗi updateQuantity:", err.message);
      toast.error(err.message);
    }
  };

  const removeFromCart = async (productId) => {
    try {
      await removeItemApi(userId, productId);
      setCart((prev) => ({
        ...prev,
        cartItems: prev.cartItems.filter(i => i.productId !== productId),
        totalPrice:
          prev.totalPrice -
          prev.cartItems.find(i => i.productId === productId).price *
          prev.cartItems.find(i => i.productId === productId).quantity
      }));
    } catch (err) {
      console.error(err);
      toast.error(err.message);
    }
  };

  const removeAllItems = async () => {
    try {
      await clearCartApi(userId);
      setCart({ cartItems: [], totalPrice: 0 });
    } catch (err) {
      console.error(err);
      toast.error(err.message);
    }
  };

  return (
    <CartContext.Provider
      value={{
        cart,
        loading,
        addToCart,
        updateQuantity,
        removeFromCart,
        removeAllItems,
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

export default CartProvider;
