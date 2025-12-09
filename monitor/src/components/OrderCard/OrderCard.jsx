import { Button } from 'react-bootstrap'
import { OrdersContext } from '../../contexts/OrdersContext'
import './OrderCard.css'
import { useContext, useState } from 'react'
import OrderDetail from '../OrderItem/OrderDetail'

function OrderCard({ order }) {

  const { updateStatus, updatePaymentStatus } = useContext(OrdersContext)
  const [open, setOpen] = useState(false)

  const handleUpdateStatus = () => {
    if (order.status === 'Pending') updateStatus(order.orderId, 'Processing')
    else if (order.status === 'Processing') updateStatus(order.orderId, 'Delivering')
    else if (order.status === 'Delivering') updateStatus(order.orderId, 'Completed')
  }

  const handleUpdatePaymentStatus = () => {
    updatePaymentStatus(order.orderId)
  }

  const renderStatusButton = () => {
    if (order.status === 'Pending')
      return <Button className={`${order.status}-btn`} onClick={() => handleUpdateStatus()}>Accept</Button>
    else if (order.status === 'Processing')
      return <Button className={`${order.status}-btn`} onClick={() => handleUpdateStatus()}>Ready for delivery</Button>
    else if (order.status === 'Delivering')
      return <Button className={`${order.status}-btn`} onClick={() => handleUpdateStatus()}>Complete</Button>
  }

  const renderPaymentStatusButton = () => {
    if (order.paymentStatus === 'UNPAID')
      return <Button style={{background:'rgb(0, 187, 12)'}} onClick={() => handleUpdatePaymentStatus()}>Paid</Button>
    else 
      return <></>
    
  }

  const date = new Date(order.orderDate);
  const datePart = date.toLocaleDateString('vi-VN');
  const timePart = date.toLocaleTimeString('vi-VN');

  return (
    <>
      <div className={`order-card ${order.status}`} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h4>#{order.orderId}</h4>
          <h6 style={{ fontWeight: 'lighter' }}>{`${datePart} ${timePart}`}</h6>
          <div>
            <span>{order.recipientName}</span>
            <span className="mx-2">•</span>
            <span>{order.recipientPhone}</span>
            <span className="mx-2">•</span>
            <span>{order.deliveryAddress}</span>
            <span className="mx-2">•</span>
            <span>{order.paymentMethod}</span>
          </div>
          <div>
            <span className="mx-2">•</span>
            <span>{order.paymentStatus}</span>
          </div>
        </div>
        <div style={{display:'flex', gap:'10px'}}>
          {renderPaymentStatusButton()}
          {renderStatusButton()}
          <Button className='show-detail-btn' onClick={() => setOpen(true)}>Xem đơn hàng</Button>
        </div>
      </div>

      <OrderDetail show={open} handleCloseModal={setOpen} order={order} />
    </>
  )
}

export default OrderCard
