import React, { useContext, useEffect, useState } from 'react'
import { NavLink, Navigate, useNavigate } from 'react-router-dom';
import { CartContext } from '../context/CartContext'
import { Form, Row, Col, Button } from 'react-bootstrap'
import { toast } from "react-toastify";
import CartItem from './components/CartItem/CartItem';
import './CheckoutPage.css'
import vnpayLogo from '../assets/checkout/vnpay.jpg'
import cash from '../assets/checkout/money.png'
import { OrderContext } from '../context/OrderContext';
import { UserContext } from '../context/UserContext';
import { VNPay } from '../api/checkoutAPI';

function CheckoutPage() {
  const { cart, removeAllItems } = useContext(CartContext);
  const { addOrder } = useContext(OrderContext)
  const { user } = useContext(UserContext)

  // Get Total
  const total = cart.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const [init_order, setInitOrder] = useState({
    customer: user.name, phone: user.phone,
    address: user.address, total: total, payment_method: ''
  })
  const [initial_order, setInitialOrder] = useState({
    recipientName: user.name,
    recipientPhone: user.phone,
    shipping_address: user.address,
    payment_method: 'COD',
    total: total
  })

  const navigate = useNavigate()

  useEffect(() => {
    if (cart.cartItems.length === 0) {
      toast.error("Giỏ hàng chưa có sản phẩm để thanh toán");
    }
  }, [cart.cartItems]);

  if (cart.cartItems.length === 0) {
    return <Navigate to="/menu" replace />; // chuyển hướng về trang menu
  }

  const handleAddOrder = async (e) => {
    e.preventDefault();
    try {
      const orderData = await addOrder(user.id, initial_order)

      // Kiểm tra phương thức thanh toán
      if (initial_order.payment_method === "VNPAY") {
        
        const res = await VNPay(orderData.orderId, orderData.totalPrice)

        if (!res.ok) {
          throw new Error("Không tạo được thanh toán VNPay");

        }

        const result = await res.json();
        console.log('paymentUrl: ', result.paymentUrl)

        if (result && result.paymentUrl) {
          // 🔹 Điều hướng sang trang thanh toán VNPay
          window.location.href = result.paymentUrl;
        } else {
          toast.warning("Không nhận được URL thanh toán từ server");
        }
      }

      await removeAllItems()
      toast.success("Đặt hàng thành công")
      navigate('/menu')
    }
    catch (err) {
      toast.warning("Lỗi")
      throw new Error(err)
    }
  }

  console.log('initial order', initial_order)

  return (
    <div style={{ paddingBlock: '16vh', marginInline: 'auto', width: '100%' }}>
      <Row style={{ paddingRight: '0' }}>

        {/* CHECKOUT INFORMATION */}
        <Col md={8}>
          <Form className='checkout-form' onSubmit={(e) => handleAddOrder(e)}>
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
                  <Form.Control className='form-control' type='text' placeholder='Nhập họ và tên người nhận'
                    value={initial_order.recipientName} required
                    onChange={(e) => setInitialOrder({ ...initial_order, recipientName: e.target.value })} />

                  <Form.Label>
                    Số điện thoại <span style={{ color: 'red', fontSize: 'smaller' }}>(Bắt buộc)</span>
                  </Form.Label>
                  <Form.Control className='form-control' type='tel'
                    placeholder='Nhập số điện thoại người nhận' pattern="^0[0-9]{9}$"
                    value={initial_order.recipientPhone} required
                    onChange={(e) => setInitialOrder({ ...initial_order, recipientPhone: e.target.value })} />

                  <Form.Label>
                    Địa chỉ nhận hàng <span style={{ color: 'red', fontSize: 'smaller' }}>(Bắt buộc)</span>
                  </Form.Label>
                  <Form.Control className='form-control' type='text' placeholder='Nhập địa chỉ nhận hàng'
                    value={initial_order.shipping_address} required
                    onChange={(e) => setInitialOrder({ ...initial_order, shipping_address: e.target.value })} />

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
                    <Form.Check type='radio' name="paymentMethod" id="payment-cod"
                      required
                      value={"COD"}
                      onChange={(e) => setInitialOrder({ ...initial_order, payment_method: e.target.value })} />
                    <Form.Label className='checkout-method-label'><img src={cash} style={{ height: '40px', marginRight: '0.8em' }}></img>Tiền mặt</Form.Label>
                  </div>
                  <div style={{ display: 'flex', gap: '1em', alignItems: 'center' }}>
                    <Form.Check type='radio' name="paymentMethod" id="payment-online"
                      required
                      value={"VNPAY"}
                      onChange={(e) => setInitialOrder({ ...initial_order, payment_method: e.target.value })} />
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
        <Col className='checkout-form' style={{ padding: '1em', borderRadius: '30px' }}>
          <div className='checkout-form'>
            <p style={{ fontSize: 'larger', borderBottom: '1px solid grey', paddingBottom: '1em' }}>Tổng cộng: {" "}
              <span style={{ fontSize: 'x-large', fontWeight: 'bold', color: '#ff8c09' }}>
                {new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(total)}
              </span>
            </p>

            {cart.cartItems.map((food) => (
              <CartItem key={food.id} item={food}></CartItem>
            ))}
          </div>
        </Col>
      </Row>
    </div >
  )
}

export default CheckoutPage
