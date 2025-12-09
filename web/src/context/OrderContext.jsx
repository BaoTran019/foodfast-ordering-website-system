import { createContext, useState, useContext, useEffect } from "react";
import { fetchOrders, addOrder as addOrderAPI } from "../api/orderAPI";

import { UserContext } from "./UserContext";

export const OrderContext = createContext(null)

const OrderProvider = ({ children }) => {

    const { user } = useContext(UserContext)
    const userId = user.id

    const [orders, setOrders] = useState([])
    const [isAddOrder, setIsAddOrder] = useState(false)

    useEffect(() => {
        const loadOrders = async () => {
            try {
                const data = await fetchOrders()
                setOrders(data.filter(data => data.userId === userId))
            }
            catch (err) {
                throw new Error(err)
            }
        }
        loadOrders();
    }, [userId, isAddOrder])

    const getOrderItem = (orderId) => {
        const order = orders.find(order => order.orderId === orderId)
        return order ? order.orderItems : []
    }

    const addOrder = async (userId, initial_order) => {
        try {
            const orderData = await addOrderAPI(userId, initial_order)
            setOrders(prev => [...prev, orderData])
            setIsAddOrder(!isAddOrder)
            return orderData
        } catch (err) {
            console.error(err)
            throw new Error(err || 'Không thể tạo order')
        }
    }

    console.log('userId for orders: ', userId)
    console.log('orders: ', orders)

    return (
        <OrderContext.Provider value={{ orders, getOrderItem, addOrder }}>
            {children}
        </OrderContext.Provider>
    )
}

export default OrderProvider