import { Children, createContext, useState } from "react";
import { logIn as logInAPI } from "../api/authenticationAPI";

export const AuthContext = createContext()

function AuthProvider({ children }) {
    const [auth, setAuth] = useState({ userId: '', token: '' })

    const logIn = async (phone, password) => {
        try {
            const data = await logInAPI(phone, password)
            setAuth({ userId: data.userId, token: data.token })
        }
        catch (err) {
            throw err;
        }
    }

    const logOut = () => {
        setAuth({ userId: '', token: '' })
    }

    return (
        <AuthContext.Provider value={{ auth, logIn, logOut }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;