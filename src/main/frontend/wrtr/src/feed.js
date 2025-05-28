import React, { useRef, useState, useEffect } from "react";
import MetaTags from "./metatags";
import Post from "./model/post";
import Posts from "./posts";

/**
 * @param {{currentUser: User?}} Logged in user
 * @returns {JSX.Element} Feed component
 */
function Feed({currentUser}) {
    const [posts, setPosts] = useState([]);
    const lastPage = useRef(0);
    const firstTimeRender = useRef(true);

    useEffect(() => {
        const setNewPage = () => {
            fetch("/api/posts/feed?page=" + lastPage.current)
                .then(response => response.json())
                .then(data => {
                    const parsed = Post.fromJson(data);
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

    if (currentUser === null) {
        return (<></>);
    }

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
