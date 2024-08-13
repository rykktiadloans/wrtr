package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.repository.PostRepository;
import com.wrtr.wrtr.core.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    public Post save(Post post){
        for(Resource resource : post.getResourceSet()){
            this.resourceRepository.save(resource);
        }
        return this.postRepository.save(post);
    }
}
