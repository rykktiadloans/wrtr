import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import MetaTags from "./metatags";

export default function Login() {
    const [searchParams, _] = useSearchParams();
    const [csrfToken, setCsrfToken] = useState("");
    useEffect(() => {
        fetch("/api/users/csrf")
            .then(response => response.json())
            .then(data => {
                setCsrfToken(data.token);
            }).catch(error => { console.log("Couldn't get CSRF token!") });
    }, [csrfToken]);
    return (<>
        <MetaTags title="Log in"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>
        <main>
            <div id="container-md my-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <h1>Login</h1>
                            </div>
                            <div class="card-body">
                            {searchParams.get("error") ? 
                                <div class="my-3">
                                    Invalid username and password.
                                </div> 
                                : <></>}
                            {searchParams.get("logout") ? 
                                <div class="my-3">
                                    You have been logged out.
                                </div> : <></>}
                                <form action="/login" method="post">
                                    <div class="form-group my-3">
                                        <input type="email" name="username" placeholder="example@sample.com" class="form-control"/>
                                    </div>
                                    <div class="form-group my-3">
                                        <input type="password" name="password" placeholder="Password" class="form-control"/>
                                    </div>
                                    <input type="hidden" name="_csrf" value={csrfToken}/>
                                    <input type="submit" value="Log in" class="btn btn-primary my-3"/>
                                </form>
                                <a href="/register" class="my-3">Register</a>

                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </main>
    </>);
}
