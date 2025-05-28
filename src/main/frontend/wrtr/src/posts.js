import React from 'react';
import { Carousel, CarouselCaption, CarouselItem } from "react-bootstrap"
import dateFormat from 'dateformat';
import Post from './model/post';

/**
 * 
 * @param {{posts: Post[], canEdit: boolean, csrfToken: string}} props
 * @returns {JSX.Element}
 */
function Posts({ posts = [], canEdit = false, csrfToken = "" }) {
    return (<>{
        posts !== [] ? 
        posts.map((post, index) => {
            return (
                <div key={post.id} className="card my-5">
                    <div className="card-header">
                        <a href={"/user/" + post.authorId}>{post.authorUsername}</a>
                    </div>
                    <div className="card-body">
                        <p className="card-text">{post.content}</p>
                        {post.images.length > 0 ?
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
                                        <CarouselItem key={image.id} className={"carousel-item" + (imageIndex === 0 ? " active" : "")}>
                                            <img src={"/" + image.path} className="d-block w-100" alt={image.name} />
                                            <CarouselCaption className="d-none d-md-block">
                                                <p>{(imageIndex + 1) + "/" + post.images.length}</p>
                                            </CarouselCaption>
                                        </CarouselItem>
                                    );
                                })}
                            </Carousel>
                            : <></>
                        }
                        {
                            post.attachments.map((attachment) => {
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
                            {canEdit ?
                                <>
                                    <form action={"/deletepost/" + post.id} method="post" className="col">
                                        <input type="hidden" name="_csrf" value={csrfToken} />
                                        <input type="hidden" name="_method" value="delete" />
                                        <input type="submit" className="btn btn-danger m-3" value="Delete post" />
                                    </form>
                                    <form action={"/deleteattachments/" + post.id} method="post" className="col">
                                        <input type="hidden" name="_csrf" value={csrfToken} />
                                        <input type="hidden" name="_method" value="delete" />
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
        :
        <p>No posts here.</p>
    }</>)
}

export default Posts;
