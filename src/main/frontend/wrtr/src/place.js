import React, { useEffect, useState } from "react";

/**
 * @param {{currentUser: User?}} Logged in user
 * @returns {JSX.Element} Place component
 */
function Place({currentUser}) {
    const [content, setContent] = useState("");
    const [timeout, setTimeoutState] = useState(-1);
    const [csrfToken, setCsrfToken] = useState("");

    useEffect(() => {
        let fetchPlace = () => {
            fetch("/api/place/")
                .then(response => {
                    return response.text();
                })
                .then(data => {
                    setContent(data);
                })
                .catch(error => console.error(error));
        };

        let fetchTimeout = () => {
            if(!currentUser) {
                return;
            }
            fetch("/api/place/timeout")
                .then(response => {
                    return response.text();
                })
                .then(data => {
                    setTimeoutState(Number(data));

                })
                .catch(error => console.error(error));
        };

        fetch("/api/users/csrf")
            .then(response => response.json())
            .then(data => {
                setCsrfToken(data.token);
            }).catch(error => { });

        fetchPlace();
        fetchTimeout();

        const placeFetcher = setInterval(() => {
            fetchTimeout();
            fetchPlace();
        }, 1000);

        return () => clearInterval(placeFetcher);

    }, [currentUser]);

    let onKeyDownHandler = (event) => {
        if (timeout > 0) {
            return;
        }

        if (event.repeat === true) {
            return;
        }

        if (event.key.length > 1 && ["Backspace", "Delete"].indexOf(event.key) === -1) {
            return;
        }

        fetch("/api/place/touch?position=" + event.target.selectionStart + "&key=" + event.key + "&_csrf=" + csrfToken, { method: "PATCH" })
            .then(response => response.text())
            .then(data => {
                if (data === "false" || data === false) {
                    return false;
                }
                else if (data === "true" || data === true) {
                    return true;
                }
                else {
                    throw Error("Something else returned: ", data);
                }
            })
            .then(ret => {
                if (ret === false) {
                    return;
                }
                setTimeoutState(60);

                fetch("/api/place/")
                    .then(response => {
                        return response.text();
                    })
                    .then(data => {
                        setContent(data);
                    })
                    .catch(error => console.log(error));

            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <>
            {currentUser && timeout > 0 ?
                <p>Time out: {timeout} seconds</p>
                :
                <></>
            }
            {!currentUser ?
                <p>You need to be logged in to participate.</p> : <></>}
            <textarea disabled={!currentUser || timeout > 0}
                className="form-control"
                onKeyDown={onKeyDownHandler} value={content}></textarea>
        </>
    );
}

export default Place;
