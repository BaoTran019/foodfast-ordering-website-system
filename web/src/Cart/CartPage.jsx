import React, { useState, useContext } from "react";
import { NavLink } from "react-router-dom";
import "./CartPage.css";
import { CartContext } from "../context/CartContext";
import { Button } from "react-bootstrap";
import { toast, Bounce } from "react-toastify";

const itemRemoveMessage = (itemName) => (
  <div>
    Đã xóa <span style={{ color: '#ff8c09' }}>{itemName}</span> khỏi giỏ hàng.
  </div>
);

function CartPage() {

  const {
    cart,
    loading,
    addToCart,
    updateQuantity,
    removeFromCart,
    removeAllItems,
  } = useContext(CartContext);
  if (loading) return <p>Đang tải giỏ hàng...</p>;
  if (!cart) return <p>Không tìm thấy giỏ hàng</p>;

  const handleRemoveItem = (item) => {
    removeFromCart(item.productId)
    toast.warning(itemRemoveMessage(item.productName))
  }

  const handleRemoveAll = () => {
    removeAllItems();
    if (cart.cartItems.length === 0) {
      toast.warning('Giỏ hàng chưa có món ăn')
    }
    else {
      toast.warning('Đã xóa tất cả khỏi giỏ hàng')
    }
  }

  const handleQty = (id, delta) => {
    const item = cart.cartItems.find(i => i.productId === id)
    if(!item) return;
    if(item.quantity===1 && delta===-1) return;
    updateQuantity(id, delta)
  }

  const handleWarning = () => toast.error('Giỏ hảng bạn chưa có món ăn')

  // Get Total
  const total = cart.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  console.log("Cart list", cart.cartItems)

  return (
    <div className="cart-page">
      <div className="cart-container" style={{ marginTop: '4vh' }}>
        <h2 className="cart-title">🛒 Giỏ hàng của bạn</h2>

        {cart.cartItems.length === 0 ? (
          <p>Giỏ hàng đang trống.</p>
        ) : (
          <div className="cart-items">
            {cart.cartItems.map(item => (
              <div className="cart-item" key={item.productId}>
                <img src={item.imageUrl} alt={item.productName} className="cart-img" />
                <div className="cart-info">
                  <h4 style={{color:'#ff8c09'}}>{item.productName}</h4>
                  <p>{new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(item.price)}</p>
                  <div className="qty-controls">
                    <button onClick={() => handleQty(item.productId, -1)}
                      disabled={item.quantity===1}>-</button>
                    <span>{item.quantity}</span>
                    <button onClick={() => handleQty(item.productId, 1)}>+</button>
                  </div>
                  <button
                    className="remove-btn"
                    onClick={() => handleRemoveItem(item)}
                  >
                    Xóa
                  </button>
                </div>
                <div className="cart-subtotal">
                  {new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(item.price * item.quantity)}
                </div>
              </div>
            ))}
          </div>
        )}

        <div className="cart-summary">
          <h3>Tổng cộng:  {" "}
            <span style={{ color: '#ff8800ff', fontWeight: 'bold' }}>
              {new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(total)}
            </span>
          </h3>
          <Button className="remove-all-btn" onClick={handleRemoveAll}>Xóa tất cả</Button>
          <Button as={NavLink} to="/menu" className="cart-summary__btn-continue" onClick={() => window.scrollTo(0, 0)}>Tiếp tục chọn món</Button>
          {cart.cartItems.length !== 0 ? (
            <Button as={NavLink} to="/checkout" className="cart-summary__btn-checkout">Thanh toán</Button>)
            : (<Button className="cart-summary__btn-checkout" onClick={handleWarning}>Thanh toán</Button>)}
        </div>
      </div>
    </div>
  );
}

export default CartPage;
