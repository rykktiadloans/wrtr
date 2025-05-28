import React, {useEffect, useState} from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Profile from "./profile";
import NavBar from "./navbar";
import Feed from "./feed";
import Home from "./home";
import { HelmetProvider } from "react-helmet-async";
import Login from "./login";
import Register from "./register";
import Logout from "./logout";

function App() {
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        fetch("/api/users/")
            .then(response => response.json())
            .then(data => {
                setCurrentUser(data);
            }).catch(error => {})
    }, []);
    
    return (
        <HelmetProvider>
            <Router>
                <NavBar currentUser={currentUser}/>
                <div>
                    <Routes>
                        <Route path="/user/:userId" element={<Profile 
                            currentUser={currentUser}/>} />
                        <Route path="/feed" element={<Feed 
                            currentUser={currentUser}/>} />
                        <Route path="/" element={<Home currentUser={currentUser} />}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/logout" element={<Logout 
                            currentUser={currentUser}/>}/>
                
                    </Routes>
                </div>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossOrigin="anonymous" />
            </Router>
        </HelmetProvider>
    );
}

export default App;
