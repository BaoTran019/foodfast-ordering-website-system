import './OrderCard.css'
import { useState } from 'react'
import OrderDetail from '../OrderItem/OrderDetail'

function OrderCard({ order }) {

  const [open, setOpen] = useState(false)

  const renderStatus = () => {
    if (order.status === 'Pending') 
      return <h6 className={`customed-header-6 status ${order.status}`}>{order.status}</h6>
    else if (order.status === 'Processing') 
      return <h6 className={`customed-header-6 status ${order.status}`}>{order.status}</h6>
    else if (order.status === 'Delivering') 
      return <h6 className={`customed-header-6 status ${order.status}`}>{order.status}</h6>
    else if (order.status === 'Completed') 
      return <h6 className={`customed-header-6 status ${order.status}`}>{order.status}</h6>
  }

  const date = new Date(order.orderDate);
  const datePart = date.toLocaleDateString('vi-VN');
  const timePart = date.toLocaleTimeString('vi-VN');

  return (
    <>
      <div className={`order-card ${order.status}`} style={{ display: 'flex', alignItems: 'center' }}
        onClick={() => setOpen(true)}>
        <h4 className='customed-header-4'><span style={{ fontWeight: 'lighter' }}>Order id: </span>#{order.orderId}</h4>
        <h6 className='customed-header-6' style={{ marginInline: '0.8rem' }}>•</h6>
        <h6 className='customed-header-6' style={{ fontWeight: 'lighter' }}><span>Date: </span>{`${datePart} ${timePart}`}</h6>
        <h6 className='customed-header-6' style={{ marginInline: '0.8rem' }}>•</h6>
        <h6 className='customed-header-6' style={{ fontWeight: 'lighter' }}>{order.paymentMethod}</h6>
        <h6 className='customed-header-6' style={{ marginInline: '0.8rem' }}>•</h6>
        <h6 className='customed-header-6' style={{ fontWeight: 'lighter' }}>{order.paymentStatus}</h6>
        <h6 className='customed-header-6' style={{ marginInline: '0.8rem' }}>•</h6>
        {renderStatus()}
      </div>

      <OrderDetail show={open} handleCloseModal={setOpen} order={order} />
    </>
  )
}

export default OrderCard
