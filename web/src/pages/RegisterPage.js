import React, {useRef} from 'react'
import {Link} from "react-router-dom";

import {useAuth} from "../context/AuthContext"

import {Navigate} from "react-router-dom"

const RegisterPage = () => {
    const usernameRef = useRef();
    const passwordRef = useRef();
    const passwordrRef = useRef();
    const {user, registerUser} = useAuth();

    if(user) return <Navigate to="/" />

    const handleRegister = (event) => {
        event.preventDefault();
        
        registerUser({
            username: usernameRef.current.value,
            password: passwordRef.current.value,
            passwordr: passwordrRef.current.value
        });
    }

    return (
        <div className="login-container">
            <form className="login__form" onSubmit={handleRegister}>
                <h1 className="login__header">Rejestracja</h1>

                <div className="login__row">
                    <label htmlFor="username">Login: </label>
                    <input type="text" id="username" ref={usernameRef} />
                </div>

                <div className="login__row">
                    <label htmlFor="password">Hasło: </label>
                    <input type="password" id="password" ref={passwordRef} />
                </div>

                <div className="login__row">
                    <label htmlFor="passwordr">Powtórz hasło: </label>
                    <input type="password" id="passwordr" ref={passwordrRef} />
                </div>

                <button type="submit">Rejestruj</button>

                <p className="register-link">Przejdź do <Link to="/login" aria-label="Zaloguj się">logowania</Link>.</p>
            </form>
        </div>
    )
}

export default RegisterPage