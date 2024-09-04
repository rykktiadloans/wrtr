package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.config.UserModelDetails;
import com.wrtr.wrtr.core.exceptions.PostNotFoundException;
import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.repository.PostRepository;
import com.wrtr.wrtr.core.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * A service that is used to work with posts
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Save a post and it's resources to the database
     * @param post The post to save
     * @return The saved post
     */
    public Post save(Post post){
        Post newPost = this.postRepository.save(post);
        for(Resource resource : post.getResourceSet()){
            this.resourceRepository.save(resource);
        }
        return newPost;
    }


    /**
     * Returns all the posts made by a user
     * @param user Author of the posts
     * @return Posts made by the user
     */
    public List<Post> getPostsMadeByUser(User user) {
        List<Post> posts = this.postRepository.getPostsMadeByUser(user);
        return posts;
    }

    /**
     * Returns post by it's id
     * @param id Id of the post
     * @return Post with the same id
     * @throws PostNotFoundException Throws if the post was not found
     */
    public Post getPostById(UUID id) throws PostNotFoundException {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isEmpty()){
            throw new PostNotFoundException(String.format("Post with id '%s' was not found", id.toString()));
        }
        return post.get();
    }

    /**
     * Deletes the post
     * @param post The post to be deleted
     */
    public void deletePost(Post post){
        this.postRepository.delete(post);
    }
}
