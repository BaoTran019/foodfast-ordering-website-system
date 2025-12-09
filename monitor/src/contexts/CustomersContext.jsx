import { createContext, useEffect, useState } from 'react'
import { fetchCustomers, changeInfo as changeInfoAPI } from '../api/customerAPI'

export const CustomersContext = createContext(null)

function CustomerProvider({ children }) {

    const [customers, setCustomers] = useState([])

    const reloadUsers = async () => {
        try {
            const newCustomers = await fetchCustomers()
            setCustomers(newCustomers)
        }
        catch (err) {
            console.log('Cannot get users')
        }
    }

    useEffect(() => { reloadUsers() }, [])

    const changeInfo = async(userId, info) => {
        try {
            await changeInfoAPI(userId, info)
            await reloadUsers()
        }
        catch (err) {
            console.log('Cannot update info')
        }
    }

    return (
        <CustomersContext.Provider value={{customers, changeInfo}}>
            {children}
        </CustomersContext.Provider>
    )
}

export default CustomerProvider
