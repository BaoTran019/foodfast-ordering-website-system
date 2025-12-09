import OrderCard from "./OrderCard/OrderCard"
import { Container, Image } from "react-bootstrap"
import { OrderContext } from "../context/OrderContext"
import { useContext } from "react"
import emptyOrder from "../assets/icons/Empty Orders_fixed.png"

function OrdersManagement() {

    const { orders } = useContext(OrderContext)
    console.log(orders)

    return (
        <Container style={{ paddingBlock: '18vh' }}>
            <div>
                {orders.length === 0 ? (
                    <div style={{ textAlign: 'center' }}>
                        <Image src={emptyOrder}
                            style={{ height: '35vh' }} />
                    </div>
                ) :
                    (
                        <div style={{background: 'white', borderRadius: '15px', padding: '20px'}}>
                            {orders.map((order) => (
                                <OrderCard order={order}></OrderCard>
                            ))}
                        </div>
                    )}

            </div>
        </Container>
    )
}

export default OrdersManagement
