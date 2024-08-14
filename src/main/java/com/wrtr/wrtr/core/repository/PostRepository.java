package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Post repository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
