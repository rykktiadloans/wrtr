import React, { useEffect, useRef, useState } from 'react';
import { useParams } from "react-router-dom";
import { Carousel, CarouselCaption, CarouselItem } from "react-bootstrap"
import dateFormat from 'dateformat';
import NewPost from './newpost';
import MetaTags from './metatags';
import Posts from "./posts";

function getDefaultUser() {
    const obj = {};
    obj.id = "id";
    obj.username = "username";
    obj.bio = "bio";
    obj.pfpPath = "pfpPath";
    return obj;
}

function jsonToUser(data) {
    const obj = {};
    obj.id = data["id"];
    obj.username = data["username"];
    obj.bio = data["bio"];
    obj.pfpPath = data["profilePicture"]["path"];
    return obj;
}

function jsonToPosts(data) {
    if(data.length === 0 || data === undefined) {
        return;
    }
    return data.map((post) => {
        post.key = post.postId;
        post.date = new Date(...post.date.splice(0, 6));
        post.images = post.resourceSet.filter((res) => {
            const extension = res.path.split(".").at(-1);
            return ["jpg", "jpeg", "png", "avif", "gif", "svg", "webp", "bmp"].indexOf(extension) !== -1; 
        });
        post.attachments = post.resourceSet.filter((res) => {
            const extension = res.path.split(".").at(-1);
            return ["jpg", "jpeg", "png", "avif", "gif", "svg", "webp", "bmp"].indexOf(extension) === -1; 
        });
        return post;
    })
}


function Profile({isLoggedIn = false}) {
    const [user, setUser] = useState(getDefaultUser());
    const [posts, setPosts] = useState(new Array());
    const [canEdit, setCanEdit] = useState(false);
    const [isFollowing, setIsFollowing] = useState(false);
    const [csrfToken, setCsrfToken] = useState("");
    const lastPage = useRef(0);
    const firstTimeRender = useRef(true);

    
    
    const userId = useParams().userId;

    useEffect(() => {
        const setNewPage = () => {
            fetch("/api/posts/?userId=" + userId + "&page=" + lastPage.current)
                .then(response => {
                    return response.json();
                }).then(data => {
                    const parsed = jsonToPosts(data);
                    if(parsed === undefined) {
                        return;
                    }
                    const result = posts.concat(parsed);
                    setPosts(result);
                    lastPage.current++;
                }).catch(error => console.log(error));
        };

        if(firstTimeRender.current) {
            setNewPage();
            firstTimeRender.current = false;

        }

        fetch("/api/users/" + userId)
            .then(response => response.json())
            .then(data => {
                setUser(jsonToUser(data));

            }).catch(error => console.log(error));

        fetch("/api/users/canEdit?userId=" + userId)
            .then(response => response.json())
            .then(data => {
                setCanEdit(data);
            }).catch(error => {});

        fetch("/api/users/csrf")
            .then(response => response.json())
            .then(data => {
                setCsrfToken(data.token);
            }).catch(error => {});

        fetch("/api/users/" + userId + "/isFollowing")
            .then(response => {return response.json();})
            .then(data => {
                if(data !== undefined){
                    setIsFollowing(data);
                }

            }).catch(error => {});

        const handleScroll = () => {
            if(window.innerHeight + window.scrollY + 20 >= document.body.offsetHeight) {
                setNewPage();
            }
        };
        
        window.removeEventListener("scroll", handleScroll);
        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, [userId, posts, lastPage]);


    return (
        <>
            <MetaTags title={user.username + " / Wrtr"} description="Wrtr userpage"/>
            <link href="/styles/profile.css" rel="stylesheet" />
            <main className="container-md">
                <div className="row justify-content-center">
                    <div className="col-md-8 ">
                        <div className="card">
                            <div className="card-header">
                                <span>{user.username}</span>
                            </div>
                            <p className="card-text">
                                {
                                    user.pfpPath === null ? 
                                        <img src="/images/emptypfp.jpg" className="pfp" alt="None set"/> :
                                        <img src={"/" + user.pfpPath } className="pfp" alt={user.username}/>
                                }
                                <br />
                                <span>{user.bio}</span>
                                { canEdit ?
                                    <div> 
                                        <a href="/editprofile" className="btn btn-primary m-3">Edit profile</a>
                                        <a href="/editpassword" className="btn btn-primary m-3">Edit password</a>

                                    </div>
                                    : <></>
                                }
                                { !canEdit && isLoggedIn && !isFollowing ? 
                                        <form action={"/api/users/" + userId + "/follow"} method="post">
                                            <input type="hidden" name="_csrf" value={csrfToken}/>
                                            <input type="hidden" name="_method" value="put" />
                                            <input type="submit" className="btn btn-primary m-3" value="Follow" />
                                        </form> : <></>
                                }
                                { !canEdit && isLoggedIn && isFollowing ? 
                                        <form action={"/api/users/" + userId + "/unfollow"} method="post">
                                            <input type="hidden" name="_csrf" value={csrfToken}/>
                                            <input type="hidden" name="_method" value="put" />
                                            <input type="submit" className="btn btn-danger m-3" value="Unfollow" />
                                        </form> : <></>
                                }

                            </p>
                        </div>
                        { canEdit ?
                                <NewPost csrfToken={csrfToken}/>
                            : <></>
                        }
                        <div>
                            <Posts posts = {posts} canEdit={canEdit} csrfToken={csrfToken}></Posts>
                        </div>
                    </div>
                </div>
            </main>
            <script src="/scripts/fileSizeValidation.js" crossOrigin='anonymous'></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossOrigin="anonymous"></script>
        </>
    );
}

export default Profile;
