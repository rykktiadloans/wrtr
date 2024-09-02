package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.User;
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
     * Fetches all posts made by the user from the database
     * @param user Author of the posts
     * @return Posts made by the user
     */
    @Query("FROM Post post WHERE post.author = :user ORDER BY post.date DESC")
    public List<Post> getPostsMadeByUser(@Param("user") User user);
}
