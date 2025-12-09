import { Children, createContext, useState, useContext, useEffect } from "react";
import { AuthContext } from "./AuthenticationContext";
import { getUser as getUserAPI, changeInfo as changeInfoAPI } from "../api/userAPI";

export const UserContext = createContext()

function UserProvider({ children }) {

  const [user, setUser] = useState({ id: '', name: '', password: '', email: '', address: '', phone: '', role: '' })

  const { auth } = useContext(AuthContext)
  const userId = auth.userId

  const [loading, setLoading] = useState(true);

  console.log("User", userId)
  console.log("User", user)

  const reloadUsers = async () => {
    if (!userId) return setLoading(false);
    try {
      const data = await getUserAPI(userId)
      setUser({
        id: data.userId, name: data.fullName, password: data.password, email: data.email,
        address: data.address, phone: data.phone, role: data.role
      })
    }
    catch (err) {
      throw err
    }
    finally {
      setLoading(false)
    }
  };


  useEffect(() => { reloadUsers() }, [userId])

  const changeInfo = async (userId, info) => {
    try {
      await changeInfoAPI(userId, info)
      await reloadUsers()
    }
    catch (err) {
      console.log('Cannot update info')
    }
  }

  return (
    <UserContext.Provider value={{ user, changeInfo }}>
      {children}
    </UserContext.Provider>
  )
}

export default UserProvider
