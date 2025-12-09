import { createContext, useEffect, useState } from 'react'
import { fetchOrders, setStatus } from '../api/orderAPI'
import { updatePaymentStatus as PaidAPI } from '../api/paymentAPI'

export const OrdersContext = createContext(null)

function OrdersProvider({ children }) {

    const [orders, setOrders] = useState([])

    const reloadOrders = async () => {
        try {
            const NewOrders = await fetchOrders()
            setOrders(NewOrders)
        } catch (err) {
            console.log('Cannot reload orders:', err)
        }
    }

    useEffect(() => { reloadOrders() }, [])

    // Status filter
    const filterOrders = (status) => {
        if (status === "all") return orders;
        return orders.filter(order => order.status === status);
    };

    // Customer filter
    const filterOrdersByCustomer = (userId) => {
        const id = Number(userId);
        return orders.filter(order => order.userId === id);
    };

    // Update stauts
    // temp
    const updateOrderStatus = (orderId, newStatus) => {

        setOrders(prevOrders =>
            prevOrders.map(order =>
                order.orderId === orderId
                    ? { ...order, status: newStatus }
                    : order
            )
        )
    }

    const updateStatus = async (orderId, newStatus) => {
        try {
            await setStatus(orderId, newStatus)
            await reloadOrders()
        }
        catch (err) {
            console.log('Cannot update order status')
        }
    }

    // Update Order's payment status
    const updatePaymentStatus = async (orderId) => {
        try {
            await PaidAPI(orderId)
            await reloadOrders()
        }
        catch (err) {
            console.log('Cannot update order payment status')
        }
    }

    // Get Order's items
    const getOrderItem = (orderId) => {
        const order = orders.find(order => order.orderId === orderId)
        return order ? order.orderItems : []
    }

    return (
        <OrdersContext.Provider value={{ orders, filterOrders, updateStatus, getOrderItem, filterOrdersByCustomer, updatePaymentStatus }}>
            {children}
        </OrdersContext.Provider>
    )
}

export default OrdersProvider
