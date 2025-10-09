import React, { useContext, useEffect } from 'react'
import { NavLink, Navigate } from 'react-router-dom';
import { CartContext } from '../context/CartContext'
import { Form, Row, Col, Button } from 'react-bootstrap'
import { toast } from "react-toastify";
import CartItem from './components/CartItem/CartItem';
import './CheckoutPage.css'
import vnpayLogo from '../assets/checkout/vnpay.jpg'
import cash from '../assets/checkout/money.png'

function CheckoutPage() {
  const { cart } = useContext(CartContext);

  // Get Total
  const total = cart.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  useEffect(() => {
    if (cart.cartItems.length === 0) {
      toast.error("Giỏ hàng chưa có sản phẩm để thanh toán");
    }
  }, [cart.cartItems]);

  if (cart.cartItems.length === 0) {
    return <Navigate to="/menu" replace />; // chuyển hướng về trang menu
  }

  return (
    <div style={{ paddingBlock: '16vh', marginInline: 'auto', width: '100%' }}>
      <Row style={{ paddingRight: '0' }}>

        {/* CHECKOUT INFORMATION */}
        <Col md={8}>
          <Form className='checkout-form'>
            <Row>
              <Col md={6} xs={12} className="mb-3">
                {/*Customer information */}
                <Form.Group className='form-group'>
                  <div className='form-group-label'>
                    Thông tin người nhận hàng
                  </div>
                  <Form.Label>
                    Họ và tên <span style={{ color: 'red', fontSize: 'smaller' }}>(Bắt buộc)</span>
                  </Form.Label>
                  <Form.Control className='form-control' type='text' placeholder='Nhập họ và tên người nhận' required />

                  <Form.Label>
                    Số điện thoại <span style={{ color: 'red', fontSize: 'smaller' }}>(Bắt buộc)</span>
                  </Form.Label>
                  <Form.Control className='form-control' type='tel' 
                    placeholder='Nhập số điện thoại người nhận' required pattern="^0[0-9]{9}$"/>

                  <Form.Label>
                    Địa chỉ nhận hàng <span style={{ color: 'red', fontSize: 'smaller' }}>(Bắt buộc)</span>
                  </Form.Label>
                  <Form.Control className='form-control' type='text' placeholder='Nhập địa chỉ nhận hàng' required />

                  <Form.Label>
                    Ghi chú
                  </Form.Label>
                  <Form.Control className='form-control' type='textarea' placeholder='Nhập ghi chú' />
                </Form.Group>
              </Col>

              <Col md={6} xs={12}>
                {/*Checkout Method */}
                <Form.Group className='form-group mb-3'>
                  <div className='form-group-label'>
                    Phương thức thanh toán
                  </div>
                  <div style={{ marginBlock: '1em', display: 'flex', gap: '1em', alignItems: 'center' }}>
                    <Form.Check type='radio' name="paymentMethod" id="payment-cod" />
                    <Form.Label className='checkout-method-label'><img src={cash} style={{ height: '40px', marginRight: '0.8em' }}></img>Tiền mặt</Form.Label>
                  </div>
                  <div style={{ display: 'flex', gap: '1em', alignItems: 'center' }}>
                    <Form.Check type='radio' name="paymentMethod" id="payment-online" />
                    <Form.Label className='checkout-method-label'><img src={vnpayLogo} style={{ height: '40px', marginRight: '0.8em' }}></img>VNPay</Form.Label>
                  </div>
                </Form.Group>

                {/*Corfirm section*/}
                <Form.Group className='confirm-section' style={{ textAlign: 'center' }}>
                  <Button className='return-to-cart-btn' as={NavLink} to="/cart">Quay lại giỏ hàng</Button>
                  <Button className='checkout-btn' type='submit'>Hoàn tất đơn hàng</Button>
                </Form.Group>
              </Col>
            </Row>
          </Form>
        </Col>

        {/*CART LIST FOR CHECKOUT*/}
        <Col className='checkout-form' style={{padding: '1em', borderRadius: '30px' }}>
          <div className='checkout-form'>
            <p style={{ fontSize: 'larger', borderBottom: '1px solid grey', paddingBottom: '1em' }}>Tổng cộng: {" "}
              <span style={{ fontSize: 'x-large', fontWeight: 'bold', color: '#ff8c09' }}>
                {new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(total)}
              </span>
            </p>

            {cart.cartItems.map((food) => (
              <CartItem item={food}></CartItem>
            ))}
          </div>
        </Col>
      </Row>
    </div >
  )
}

export default CheckoutPage
