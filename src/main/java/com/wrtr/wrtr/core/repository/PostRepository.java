package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Post repository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    /**
     * Specifies the page size when fetching a list of posts
     */
    public int PAGE_SIZE = 5;

    /**
     * Fetches all posts made by the user from the database
     * @param user Author of the posts
     * @return Posts made by the user
     */
    @Query("FROM Post post WHERE post.author = :user ORDER BY post.date DESC")
    public List<Post> getPostsMadeByUser(@Param("user") User user);


    /**
     * Fetches all posts made by the user from the database in pages
     * @param user Author of the posts
     * @param pageable An object that specifies a page of the request
     * @return Posts made by the user
     */
    @Query("FROM Post post WHERE post.author = :user ORDER BY post.date DESC")
    public Page<Post> getPostsMadeByUser(@Param("user") User user, Pageable pageable);
}
