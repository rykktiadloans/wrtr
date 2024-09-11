import React, { useEffect, useState } from 'react';
import { useLocation, useParams } from "react-router-dom";
import ReactDOM from 'react-dom/client';

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
    }, []);


    return (
        <>
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
                                {
                                    /*
                                <div th:if="${canEdit}">
                                    <a th:href="@{/editprofile}" className="btn btn-primary m-3">Edit profile</a>
                                    <a th:href="@{/editpassword}" className="btn btn-primary m-3">Edit password</a>

                                </div>
                                    */
                                }

                            </p>
                        </div>
                        { /*
                        <div th:if="${canEdit}" className="card my-5">
                            <form action="#" th:action="@{/newpost}" th:object="${postDto}" method="post" enctype="multipart/form-data" className="card-body">
                                <div className="form-group my-2">
                                    <textarea th:field="*{content}" className="form-control" maxlength="8192" required></textarea>
                                </div>
                                <div className="form-group my-2">
                                    <p id="file-errors"></p>
                                    <input type="file" th:field="*{files}" className="from-control-file" multiple />
                                </div>
                                <input value="Submit" type="submit" className="btn btn-primary my-2" />

                            </form>

                        </div>
                            */}
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
                                                    <div id={'carousel' + index} className="carousel carsize slide " >
                                                        <div className="carousel-inner">
                                                            {post.images.map((image, imageIndex) => {
                                                                return (
                                                                    <div key={image.id + imageIndex} className={"carousel-item" + (imageIndex === 0 ? " active" : "")}>
                                                                        <img src={"/" + image.path} className="d-block w-100" />
                                                                        <div className="carousel-caption d-none d-md-block">
                                                                            <p>{(imageIndex + 1) + "/" + post.images.length }</p>
                                                                        </div>
                                                                    </div>
                                                                );
                                                            })}
                                                        </div>
                                                        <button className="carousel-control-prev" type="button" data-bs-target={'#carousel' + index} data-bs-slide="prev">
                                                            <span className="carousel-control-prev-icon" aria-hidden="true"><b>{'<'}</b></span>
                                                            <span className="visually-hidden">Previous</span>
                                                        </button>
                                                        <button className="carousel-control-next" type="button" data-bs-target={'#carousel' + index} data-bs-slide="next">
                                                            <span className="carousel-control-next-icon" aria-hidden="true"><b>{'>'}</b></span>
                                                            <span className="visually-hidden">Next</span>
                                                        </button>
                                                    </div>
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
                                                <small className="text-body-secondary">{post.date}</small>

                                            </div>
                                            <div className="container">
                                                <div className="row">
                                        { /*
                                            <form th:if="${canEdit}" action="#" th:action="@{/deletepost} + '/' + ${post.id}" th:method="delete" className="col">
                                            <input type="submit" className="btn btn-danger m-3" value="Delete post" />

                                            </form>
                                            <form th:if="${canEdit}" action="#" th:action="@{/deleteattachments} + '/' + ${post.id}" th:method="delete" className="col">
                                            <input type="submit" className="btn btn-warning m-3" value="Delete attachments" />

                                            </form>
                                        */ }
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
