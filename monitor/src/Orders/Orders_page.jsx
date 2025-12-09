import { Button, Container } from 'react-bootstrap'
import './Orders_page.css'
import { useState } from 'react'
import OrderList from '../components/OrderList/OrderList'

function Orders_page() {

  const [filter, setFilter] = useState('Pending')

  const buttons = [
    {id: 'Pending', label:'New Orders'},
    {id: 'Processing', label:'In Progress'},
    {id: 'Delivering', label:'Ready for Delivery'},
    {id: 'Completed', label:'Completed'},
  ]

  return (
    <div>
      {/* ORDERS FILTER */}
      <Container className='orders-filter'>
        {buttons.map((btn)=>(
          <Button
          key={btn.id}
          id={btn.id}
          className={filter === btn.id ? "active" : ""}
          onClick={() => setFilter(btn.id)}
          >
            {btn.label}
          </Button>
        ))}
      </Container>

      {/* LIST OF ORDERS */}
      <Container>
        <OrderList status={filter}/>
      </Container>
    </div>
  )
}

export default Orders_page
