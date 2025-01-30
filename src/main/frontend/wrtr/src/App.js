import React, {useState} from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Profile from "./profile";
import NavBar from "./navbar";
import Feed from "./feed";
import { HelmetProvider } from "react-helmet-async";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    fetch("/api/users/isLoggedIn")
        .then(response => response.json())
        .then(data => {
            setIsLoggedIn(data);
        }).catch(error => {});
    
    return (
        <HelmetProvider>
            <Router>
                <NavBar isLoggedIn={isLoggedIn}/>
                <div>
                    <Routes>
                        <Route path="/user/:userId" element={<Profile isLoggedIn={isLoggedIn}/>} />
                        <Route path="/feed" element={<Feed isLoggedIn={isLoggedIn} />}/>
                    </Routes>
                </div>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossOrigin="anonymous" />
            </Router>
        </HelmetProvider>
    );
}

export default App;
