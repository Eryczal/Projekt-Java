import React, {useContext, createContext, useState, useEffect} from 'react'

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

const AuthContextProvider = ({children}) => {
    const [user, setUser] = useState();
    const [token, setToken] = useState();

    const signOut = () => {
        setUser(null);
        setToken(null);
    }

    useEffect(() => {
        if(!token) return;

        fetch(process.env.REACT_APP_URL + "/api/auth/user", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => {
            if(response.ok) {
                return response.json();
            } else {
                return Promise.reject("Błąd " + response.status + ": " + response.statusText);
            }
        })
        .then(response => {
            if(response) {
                setUser(response);
            }
        })
    }, [token]);

    const authFetch = ({username, password, newUser = false}) => {
        fetch(process.env.REACT_APP_URL + `/api/auth/${newUser ? "register" : "login"}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username,
                password
            })
        })
        .then(response => {
            if(response.ok) {
                return response.json();
            } else {
                return Promise.reject("Błąd " + response.status + ": " + response.statusText);
            }
        })
        .then(response => {
            if(response.success) {
                setToken(response.token);
            }
        })
        .catch(error => {
            console.error(error);
        });
    }

    const signIn = async ({password, username}) => {
        authFetch({password, username});
    }

    const registerUser = ({username, password, passwordr}) => {
        if(password == passwordr) {
            authFetch({password, username, newUser: true});
        }
    }

    const value = {
        user,
        signOut,
        signIn,
        registerUser,
        token
    }

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export default AuthContextProvider;