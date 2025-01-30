package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.dto.PostApiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Repository of posts
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {

    /**
     * Get a list of resources that belong to the supplied list of posts
     * @param posts Posts
     * @return Resources of those posts
     */
    @Query("FROM Resource resource WHERE resource.post.id in (:post_ids) ORDER BY post.date DESC")
    public List<Resource> getResourcesOfPosts(@Param("post_ids") List<UUID> posts);
}
