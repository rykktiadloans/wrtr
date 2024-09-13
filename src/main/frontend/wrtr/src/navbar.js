import React, { useState } from "react";


function NavBar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    fetch("/api/users/isLoggedIn").
        then(response => response.json()).
        then(data => setIsLoggedIn(data));


    return (
        <>
            <nav id="header" className="navbar navbar-expand-md bg-body-tertiary">
                <div className="container-fluid">
                    <div>
                        <a href="/" className="navbar-brand">Wrtr</a>
                    </div>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-md-0">
                            { isLoggedIn ? 
                                <>
                                    <li className="nav-item">
                                        <a href="/myprofile" className="nav-link">My profile</a>
                                    </li>
                                    <li className="nav-item">
                                        <a href="/logout" className="nav-link">Log out</a>
                                    </li>
                                </>
                                :
                                <>
                                    <li className="nav-item">
                                        <a href="/login" className="nav-link">Log in</a>
                                    </li>
                                    <li className="nav-item">
                                        <a href="/register" className="nav-link">Register</a>
                                    </li>
                                </>

                            }

                        </ul>
                        <form className="d-flex" role="search" action="/search/users" method="get">
                            <input name="username" className="form-control me-2" type="search" placeholder="Search users" aria-label="Search"/>
                            <button className="btn btn-outline-success" type="submit">Search</button>
                        </form>

                    </div>
                </div>
            </nav>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossOrigin="anonymous"></script>
        </>
    );
}

export default NavBar;
