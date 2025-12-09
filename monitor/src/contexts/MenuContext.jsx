import { createContext, useState, useEffect } from 'react'
import { food_list } from '../assets/mock_data/menu'
import { fetchMenu, setStatus as setStatusAPI, changeInfo, addProduct as addProductAPI, deleteProduct as deleteAPI } from '../api/productAPI'

export const MenuContext = createContext(null)

function MenuProvider({ children }) {

    const [menu, setMenu] = useState([])

    const reloadMenu = async () => {
        try {
            const updatedMenu = await fetchMenu()
            setMenu(updatedMenu)
        } catch (err) {
            console.log('Cannot reload menu:', err)
        }
    }

    useEffect(() => { reloadMenu() }, [])

    const setStatus1 = (id, status) => {
        setMenu(prevItem =>
            prevItem.map(item =>
                item.productId === id
                    ? { ...item, available: status }
                    : item
            )
        )
    }

    const setStatus = async (productId, status) => {
        try {
            const update = await setStatusAPI(productId, status)
            await reloadMenu()
            setMenu(prevItem =>
                prevItem.map(item =>
                    item.productId === productId
                        ? { ...item, available: status }
                        : item
                )
            )
        }
        catch (err) {
            console.log('Cannot update product status')
        }
    }

    const editInfo = async (productId, edittedItem) => {
        try {
            await changeInfo(productId, edittedItem)
            await reloadMenu()
        }
        catch (err) {
            console.log('Cannot edit product information')
        }
    }

    const addProduct = async (addedItem) => {
        try {
            await addProductAPI(addedItem)
            await reloadMenu()
        }
        catch (err) {
            console.log('Cannot add product')
        }
    }

    const deleteProduct = async (productId) => {
        try {
            await deleteAPI(productId)
            await reloadMenu()
        }
        catch (err) {
            consosle.log('Cannot delete product')
        }
    }

    return (
        <MenuContext.Provider value={{ menu, setStatus, editInfo, addProduct, deleteProduct }}>
            {children}
        </MenuContext.Provider>
    )
}

export default MenuProvider
