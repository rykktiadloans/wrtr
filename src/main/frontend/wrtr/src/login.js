import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import MetaTags from "./metatags";

/**
 * @returns {JSX.Element} Login component
 */
export default function Login() {
    const [searchParams, _] = useSearchParams();
    const [csrfToken, setCsrfToken] = useState("");
    useEffect(() => {
        fetch("/api/users/csrf")
            .then(response => response.json())
            .then(data => {
                setCsrfToken(data.token);
            }).catch(error => { console.log("Couldn't get CSRF token!") });
    }, []);
    return (<>
        <MetaTags title="Log in"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>
        <main>
            <div id="container-md my-5">
                <div className="row justify-content-center">
                    <div className="col-md-8">
                        <div className="card">
                            <div className="card-header">
                                <h1>Login</h1>
                            </div>
                            <div className="card-body">
                            {searchParams.has("error") ? 
                                <div className="my-3">
                                    Invalid username and password.
                                </div> 
                                : <></>}
                            {searchParams.has("logout") ? 
                                <div className="my-3">
                                    You have been logged out.
                                </div> : <></>}
                                <form action="/login" method="post">
                                    <div className="form-group my-3">
                                        <input type="email" name="username" placeholder="example@sample.com" className="form-control"/>
                                    </div>
                                    <div className="form-group my-3">
                                        <input type="password" name="password" placeholder="Password" className="form-control"/>
                                    </div>
                                    <input type="hidden" name="_csrf" value={csrfToken}/>
                                    <input type="submit" value="Log in" className="btn btn-primary my-3"/>
                                </form>
                                <a href="/register" className="my-3">Register</a>

                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </main>
    </>);
}
