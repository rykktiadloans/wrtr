import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import MetaTags from "./metatags";

/**
 * @returns {JSX.Element} Register component
 */
export default function Register() {
    const [searchParams, ] = useSearchParams();
    const [csrfToken, setCsrfToken] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const arePasswordsEqual = password === confirmPassword;
    const error = searchParams.has("error");
    const duplicate = searchParams.has("duplicate");
    const logout = searchParams.has("logout");
    const maxlen = searchParams.has("maxlen");

    useEffect(() => {
        fetch("/api/users/csrf")
            .then(response => response.json())
            .then(data => {
                setCsrfToken(data.token);
            }).catch(error => { console.log("Couldn't get CSRF token!") });
    }, []);

    return (<>
        <MetaTags title="Register"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>

        <main>
            <div id="container-md my-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <h1>Register</h1>
                            </div>
                            <div class="card-body">
                                {error ? 
                                    <div class="my-3">
                                        Invalid username and password.
                                    </div> : <></>
                                }
                                {duplicate ?
                                    <div class="my-3">
                                        There already exists a user with the same email address.
                                        </div> : <></>
                                }
                                {logout ? 
                                    <div class="my-3">
                                        You have been logged out.
                                    </div> : <></>
                                }
                                {maxlen ? 
                                    <div class="my-3">
                                        Email and password must be at most 255 characters long
                                    </div> : <></>
                                }
                                {!arePasswordsEqual ? 
                                    <div  id="password-fail" class="my-3">
                                        Passwords must be the same
                                    </div> : <></>}
                                <form action="/register" method="post">
                                    <div class="form-group my-3">
                                        <input name="email" type="email" placeholder="example@sample.com" class="form-control" maxlength="255" required/>
                                    </div>
                                    <div class="form-group my-3">
                                        <input type="password" name="password" placeholder="Password" class="form-control" maxlength="255" minlength="8" required value={password} 
                                            onChange={(e) => setPassword(e.target.value)}/>
                                    </div>
                                    <div class="form-group my-3">
                                        <input type="password" id="password2" placeholder="Confirm password" class="form-control" maxlength="255" minlength="8" required value={confirmPassword}
                                            onChange={(e) => setConfirmPassword(e.target.value)}/> 
                                    </div>
                                    <input type="hidden" name="_csrf" value={csrfToken}/>
                                    <input type="submit" value="Register" class="btn btn-primary my-3"/>
                                </form>
                                <a href="/login" class="my-3">Log in</a>

                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </main>
        </>);
}
