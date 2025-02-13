import React from "react";
import Place from "./place";

function Home({ isLoggedIn = false }) {

    return (
        <main>
            <div className="container-md my-5">
                <div className="row justify-content-center">
                    <div className="col-md-8">
                        <h1>Wrtr</h1>
                        <h3>A simple blogging platform. Sometimes simplicity is all you need.</h3>
                        <p>This website allows you to make posts on your page. And nothing more than that.</p>
                        <p>Get started now!</p>
                        <a className="btn btn-primary" href="/register">Register</a>
                        <br/>
                        <br/>
                        <br/>

                        <h2>Place canvas</h2>
                        <p>Place canvas is an interactive text playground which every user can edit. The only caveat is that each user is permitted to add or remove <em>one</em> character every minute</p>
                        <Place isLoggedIn={isLoggedIn}/>
                    </div>
                </div>
            </div>
        </main>

    );
}

export default Home;
