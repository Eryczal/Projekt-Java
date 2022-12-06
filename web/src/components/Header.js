import React from 'react'

import {Link} from "react-router-dom";

import {useAuth} from "../context/AuthContext"

const HeaderLinks = () => {
    
    const { user, signOut } = useAuth();

    return(
        <>
            <li><Link to="/">Strona Główna</Link></li>
            <li>Zalogowano jako {user.username} (<span onClick={signOut}>Wyloguj się</span>)</li>
        </> 
    )
}

const Header = () => {
    return (
        <header className="header">
            <div className="site-name">
                <h1>ToDo App</h1>
            </div>
            <div className="site-nav">
                <nav>
                    <ul>
                       <HeaderLinks />
                    </ul>
                </nav>
            </div>
        </header>
    )
}

export default Header