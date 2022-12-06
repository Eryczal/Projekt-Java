import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import RegisterPage from "./pages/RegisterPage";
import ProtectedRoute from "./components/ProtectedRoute";

import {createBrowserRouter, RouterProvider} from "react-router-dom";
import AuthContextProvider from "./context/AuthContext";

const router = createBrowserRouter([{
    path: "/",
    element: <ProtectedRoute><HomePage /></ProtectedRoute>
},
{
    path: "/login",
    element: <LoginPage />
},
{
    path: "/register",
    element: <RegisterPage />
}
])

function App() {
    return (
        <AuthContextProvider>
            <RouterProvider router={router} />
        </AuthContextProvider>
    );
}

export default App;
