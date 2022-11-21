const LoginPage = () => {
    return (
        <div className="login-container">
            <form className="login-form">
                <label htmlFor="username">Login:</label>
                <input type="text" id="username" />

                <label htmlFor="password">Hasło:</label>
                <input type="password" id="password" />

                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default LoginPage;