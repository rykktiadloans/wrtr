import { useEffect, useState } from "react";
import MetaTags from "./metatags";

export default function Logout({isLoggedIn = false}) {
    const [csrfToken, setCsrfToken] = useState("");
    useEffect(() => {
        fetch("/api/users/csrf")
            .then(response => response.json())
            .then(data => {
                setCsrfToken(data.token);
            }).catch(error => { console.log("Couldn't get CSRF token!") });
    }, [csrfToken]);
    return (<>
                <MetaTags title="Log out"/>

                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>
                <main>
                    <div class="container-md my-5">
                        <div class="row justify-content-center">
                            <div class="col-md-8">
                                {isLoggedIn ?
                                    <>
                                        <h2>You sure you want to log out?</h2>
                                        <form action="/logout" method="post">
                                            <button type="submit" class="btn btn-primary my-3">Log out</button>
                                        </form>
                                    </>
                                    : 
                                    <h2>You need to be logged in to be able to log out</h2>
                                }
                            </div>
                        </div>
                    </div>
                </main>
        </>);

}
