import { Children, createContext, useState, useEffect } from "react";
import { logIn as logInAPI } from "../api/authAPI";
import { toast } from "react-toastify";

export const AuthContext = createContext()

function AuthProvider({ children }) {
    const [auth, setAuth] = useState({ userId: '', token: '' })

    useEffect(() => {
        const storedAuth = localStorage.getItem("auth");
        if (storedAuth) {
            setAuth(JSON.parse(storedAuth));
        }
    }, []);

    const logIn = async (phone, password) => {
        try {
            const data = await logInAPI(phone, password)

            if (data.userId !== 1) {
                return
            }

            setAuth({ userId: data.userId, token: data.token })

            localStorage.setItem(
                "auth",
                JSON.stringify({ userId: data.userId, token: data.token })
            )
        }
        catch (err) {
            throw err;
        }
    }

    const logOut = () => {
        setAuth({ userId: '', token: '' })
        localStorage.removeItem("auth");
    }

    return (
        <AuthContext.Provider value={{ auth, logIn, logOut }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;