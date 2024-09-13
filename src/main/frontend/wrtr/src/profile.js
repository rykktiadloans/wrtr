import React, { useEffect, useRef, useState } from 'react';
import { useLocation, useParams } from "react-router-dom";
import ReactDOM from 'react-dom/client';
import { Carousel, CarouselCaption, CarouselItem } from "react-bootstrap"
import dateFormat from 'dateformat';
import NewPost from './newpost';
import MetaTags from './metatags';

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
    return data.map((post) => {
        post.key = post.id;
        post.date = new Date(...post.date.splice(0, 6));
        post.images = post.resourceSet.filter((res) => {
            const extension = res.path.split(".").at(-1);
            return ["jpg", "jpeg", "png", "avif", "gif", "svg", "webp", "bmp"].indexOf(extension) != -1; 
        });
        post.attachments = post.resourceSet.filter((res) => {
            const extension = res.path.split(".").at(-1);
            return ["jpg", "jpeg", "png", "avif", "gif", "svg", "webp", "bmp"].indexOf(extension) == -1; 
        });
        return post;
    })
}


function Profile(props) {
    const [user, setUser] = useState(getDefaultUser());
    const [posts, setPosts] = useState([]);
    const [canEdit, setCanEdit] = useState(false);
    const [csrfToken, setCsrfToken] = useState("")
    
    
    const userId = useParams().userId;

    useEffect(() => {

        fetch("/api/users/" + userId).
            then(response => response.json()).
            then(data => {
                setUser(jsonToUser(data));

            });

        fetch("/api/posts/?userId=" + userId).
            then(response => response.json()).
            then(data => {
                setPosts(jsonToPosts(data));
            });
        fetch("/api/users/canEdit?userId=" + userId).
            then(response => response.json()).
            then(data => {
                setCanEdit(data);
            });
        fetch("/api/users/csrf").
            then(response => response.json()).
            then(data => {
                setCsrfToken(data.token);
            });
    }, []);


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
                                        <img src="/images/emptypfp.jpg" className="pfp" /> :
                                        <img src={"/" + user.pfpPath } className="pfp" />
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

                            </p>
                        </div>
                        { canEdit ?
                                <NewPost csrfToken={csrfToken}/>
                            : <></>
                        }
                        <div>
                            {
                                posts.map((post, index) => {
                                    return (
                                        <div key={post.id + index} className="card my-5">
                                            <div className="card-header">
                                                <span>{user.username}</span>
                                            </div>
                                            <div className="card-body">
                                                <p className="card-text">{post.content}</p>
                                                { post.images.length > 0 ? 
                                                    <Carousel prevIcon={
                                                        <span>
                                                            <span className="carousel-control-prev-icon" aria-hidden="true"><b>{'<'}</b></span>
                                                        </span>
                                                    } nextIcon={
                                                        <span>
                                                            <span className="carousel-control-prev-icon" aria-hidden="true"><b>{'>'}</b></span>
                                                        </span>
                                                    }
                                                    className="carsize" wrap={false} interval={null} indicators={false}>
                                                        {post.images.map((image, imageIndex) => {
                                                            return (
                                                                <CarouselItem key={image.id + imageIndex} className={"carousel-item" + (imageIndex === 0 ? " active" : "")}>
                                                                    <img src={"/" + image.path} className="d-block w-100" />
                                                                    <CarouselCaption className="d-none d-md-block">
                                                                        <p>{(imageIndex + 1) + "/" + post.images.length }</p>
                                                                    </CarouselCaption>
                                                                </CarouselItem>
                                                            );
                                                        })}
                                                    </Carousel>
                                                    : <></>
                                                }
                                                {                                                     
                                                    post.attachments.map((attachment, attachmentIndex) => { 
                                                        return (
                                                            <div key={attachment.id}>
                                                                <a href={"/" + attachment.path} download>{attachment.name}</a>
                                                            </div>
                                                        );
                                                    })
                                                }
                                                <small className="text-body-secondary">{dateFormat(post.date, "dd mmm, yyyy HH:MM")}</small>

                                            </div>
                                            <div className="container">
                                                <div className="row">
                                        { canEdit ?
                                            <>
                                                <form action={"/deletepost/" + post.id} method="post" className="col">
                                            <input type="hidden" name="_csrf" value={csrfToken}/>
                                                    <input type="hidden" name="_method" value="delete"/>
                                                    <input type="submit" className="btn btn-danger m-3" value="Delete post" />
                                                </form>
                                                <form action={"/deleteattachments/" + post.id} method="post" className="col">
                                            <input type="hidden" name="_csrf" value={csrfToken}/>
                                                    <input type="hidden" name="_method" value="delete"/>
                                                    <input type="submit" className="btn btn-warning m-3" value="Delete attachments" />
                                                </form>
                                            </>
                                            : <></>
                                        }
                                                </div>
                                            </div>
                                        </div>

                                    );
                                })
                            }
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
