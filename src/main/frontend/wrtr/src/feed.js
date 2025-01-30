import React, { useRef, useState, useEffect } from "react";
import MetaTags from "./metatags";
import Posts from "./posts";

function jsonToPosts(data) {
    if (data.length === 0 || data === undefined) {
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

function Feed({ isLoggedIn = false }) {
    const [posts, setPosts] = useState([]);
    const lastPage = useRef(0);
    const firstTimeRender = useRef(true);

    useEffect(() => {
        const setNewPage = () => {
            fetch("/api/posts/feed?page=" + lastPage.current)
                .then(response => response.json())
                .then(data => {
                    const parsed = jsonToPosts(data);
                    if (parsed === undefined) {
                        return;
                    }
                    const result = posts.concat(parsed);
                    setPosts(result);
                    lastPage.current++;
                })
                .catch(error => { });
        };

        if (firstTimeRender.current) {
            setNewPage();
            firstTimeRender.current = false;

        }

        const handleScroll = () => {
            if (window.innerHeight + window.scrollY + 20 >= document.body.offsetHeight) {
                setNewPage();
            }
        };

        window.removeEventListener("scroll", handleScroll);
        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, [posts, lastPage]);

    return (
        <>
            <MetaTags title={"Feed / Wrtr"} description="Wrtr feed" />
            <link href="/styles/profile.css" rel="stylesheet" />
            <main className="container-md">
                <div className="row justify-content-center">
                    <div className="col-md-8 ">
                        <Posts posts={posts} canEdit={false} csrfToken={""}/>

                    </div>
                </div>
            </main>
            <script src="/scripts/fileSizeValidation.js" crossOrigin='anonymous'></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossOrigin="anonymous"></script>
        </>

    );


}

export default Feed;
