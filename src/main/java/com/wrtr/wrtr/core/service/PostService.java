package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.config.UserModelDetails;
import com.wrtr.wrtr.core.exceptions.PostNotFoundException;
import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.repository.PostRepository;
import com.wrtr.wrtr.core.repository.ResourceRepository;
import com.wrtr.wrtr.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    @Autowired
    private UserRepository userRepository;

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
     * Returns all the posts made by a user
     * @param user Author of the posts
     * @param pageable An object that specifies a page
     * @return Posts made by the user
     */
    public Page<Post> getPostsMadeByUser(User user, Pageable pageable) {
        Page<Post> posts = this.postRepository.getPostsMadeByUser(user, pageable);
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
        post.getAuthor().getPostList().remove(post);
        this.userRepository.save(post.getAuthor());
        this.postRepository.delete(post);
    }

    /**
     * Delete attachments of the supplied post
     * @param post Post whose attachments we want to delete
     */
    public void deletePostsAttachments(Post post){
        for(Resource resource : post.getResourceSet()){
            this.resourceRepository.delete(resource);
        }
        post.getResourceSet().clear();
        this.save(post);

    }
}
