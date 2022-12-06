import {Link} from "react-router-dom";
import {useRef} from "react";

import {useAuth} from "../context/AuthContext"

import {Navigate} from "react-router-dom"

const LoginPage = () => {
    const usernameRef = useRef();
    const passwordRef = useRef();
    const {user, signIn} = useAuth();

    if(user) return <Navigate to="/" />

    const handleLogin = (event) => {
        event.preventDefault();

        signIn({
            username: usernameRef.current.value,
            password: passwordRef.current.value
        })
    }

    return (
        <div className="login-container">
            <form className="login__form" onSubmit={handleLogin}>
                <h1 className="login__header">Logowanie</h1>

                <div className="login__row">
                    <label htmlFor="username">Login: </label>
                    <input type="text" id="username" ref={usernameRef} />
                </div>

                <div className="login__row">
                    <label htmlFor="password">Hasło: </label>
                    <input type="password" id="password" ref={passwordRef} />
                </div>

                <button type="submit">Login</button>

                <p className="register-link">Przejdź do <Link to="/register" aria-label="Zarejestruj się">rejestracji</Link>.</p>
            </form>
        </div>
    );
};

export default LoginPage;